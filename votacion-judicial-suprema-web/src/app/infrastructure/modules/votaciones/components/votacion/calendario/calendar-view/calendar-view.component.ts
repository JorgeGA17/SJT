import { Component, computed, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CalendarOverlayComponent } from '../calendar-overlay/calendar-overlay.component';
import { CalendarService } from './calendar.service';
import { DiaCalendario } from '../../../../../../../domain/dto/local/DiaCalendario';
import { CasacionesService } from '../../../../../../services/remote/casaciones-service';
import { Instancia, Usuario } from '../../../../../../../domain/dto/remote/LoginResponse.dto';
import { LocalStorageUsuarioService } from '../../../../../../services/local/local-storage-usuario.service';
import { take } from 'rxjs';
import { CasacionesRquestParams, CasacionesRquestPath } from '../../../../../../../domain/dto/remote/CasacionesRequest.dto';
import { CasacionDataModel } from '../../../../../../../domain/models/Casacion.model';
import { CasacionesResponse } from '../../../../../../../domain/dto/remote/CasacionesResponse.dto';
import { constantes } from '../../../../../../../domain/commons/constants';
import Swal from 'sweetalert2';
import { AppVjState } from '../../../../../../global-store/vj.reducers';
import { Store } from '@ngrx/store';
import * as actions from '../../../../../../global-store/vj.actions';
import { AppVotacionesState } from '../../../../store/votaciones.reducers';
import * as actionsVotaciones from '../../../../store/actions';


@Component({
  selector: 'app-calendar-view',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatDialogModule
  ],
  templateUrl: './calendar-view.component.html',
  styleUrls: ['./calendar-view.component.scss']
})
export class CalendarViewComponent implements OnInit{
  private dialog = inject(MatDialog);
  constructor(private router: Router, 
    private casacionesService: CasacionesService,
    private localStorageUsuarioService: LocalStorageUsuarioService,
    private store: Store<AppVjState>,
    private storeVotaciones: Store<AppVotacionesState>
  ){}

  usuario:Usuario | null = null;
  sala: Instancia | null = null;

  // Días de la semana
  weekDays = ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'];

  // Señal para el mes/año actual
  currentDate = signal<Date>(new Date());

  // Señal para la lista de días del calendario
  diasCalendario = signal<DiaCalendario[]>([]);

  // Computed para el nombre del mes
  monthName = computed(() => {
    const date = this.currentDate();
    return date.toLocaleDateString('es-ES', { month: 'long', year: 'numeric' });
  });

  // Computed para obtener los días del calendario (para el template)
  calendarDays = computed(() => {
    return this.diasCalendario().map(dia => dia.fecha);
  });

  ngOnInit(): void {
    this.cargarEventosCalendario();
    this.localStorageUsuarioService.getUsuario().pipe(take(1))
    .subscribe(usuario => {
      if(usuario)this.usuario = {...usuario};
    });
    this.localStorageUsuarioService.getSala().pipe(take(1))
      .subscribe(sala => {
      if(sala) this.sala = {...sala};
    });
  }


  // Cargar eventos del calendario
  private cargarEventosCalendario(): void {
    const date = this.currentDate();
    const year = date.getFullYear();
    const month = date.getMonth();

    // Calcular fecha inferior y superior del calendario
    const { fechaInicio, fechaFin } = this.calcularRangoFechasCalendario(year, month);

    // Formatear fechas para el servicio (formato ISO)
    const fechaInicioStr = this.formatearFechaParaServicio(fechaInicio);
    const fechaFinStr = this.formatearFechaParaServicio(fechaFin);

    const casacionesParams:CasacionesRquestParams= {
      fecha_inicio: fechaInicioStr,
      fecha_fin:fechaFinStr
    }

    // Consultar el servicio
    if(!this.usuario || !this.sala){
      return;
    }
    const casacionesPath:CasacionesRquestPath={
      distrito: this.sala.codigoDistrito.trim(),
      provincia: this.sala.codigoProvincia.trim(),
      instancia: this.sala.codigoInstancia.trim()
    } 
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.casacionesService.getCasaciones(casacionesParams, casacionesPath).subscribe({
      next: (response: CasacionesResponse) => {
        if(response.codigo === constantes.RES_COD_EXITO){
          const diasGenerados = this.generarDiasCalendario(year, month, response.data);
          this.diasCalendario.set(diasGenerados);
        }
      },
      complete:() =>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
      },
      error: (error) => {
        Swal.fire("Atención",error);
        const diasGenerados = this.generarDiasCalendario(year, month, []);
        this.diasCalendario.set(diasGenerados);
      }
    });
  }

  // Calcular rango de fechas del calendario (incluyendo días de meses anteriores/siguientes)
  private calcularRangoFechasCalendario(year: number, month: number): { fechaInicio: Date, fechaFin: Date } {
    const primerDiaMes = new Date(year, month, 1);
    const primerDiaSemana = primerDiaMes.getDay(); // 0 = domingo

    // Fecha de inicio: primer día visible en el calendario
    const fechaInicio = new Date(year, month, 1);
    fechaInicio.setDate(1 - primerDiaSemana);

    // Fecha de fin: último día visible en el calendario (42 días = 6 semanas)
    const fechaFin = new Date(fechaInicio);
    fechaFin.setDate(fechaInicio.getDate() + 41);

    return { fechaInicio, fechaFin };
  }

  // Formatear fecha para el servicio (formato ISO)
  private formatearFechaParaServicio(fecha: Date): string {
    return fecha.toISOString().split('T')[0]; // Formato: "2025-06-26"
  }

  // Generar lista de días del calendario y agrupar eventos
  private generarDiasCalendario(year: number, month: number, eventos: CasacionDataModel[]): DiaCalendario[] {
    const dias: DiaCalendario[] = [];
    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);

    // Agrupar eventos por día
    const eventosPorDia = this.agruparEventosPorDia(eventos);

    // Generar días del calendario
    const { fechaInicio } = this.calcularRangoFechasCalendario(year, month);

    // Generar 42 días (6 semanas)
    for (let i = 0; i < 42; i++) {
      const fecha = new Date(fechaInicio);
      fecha.setDate(fechaInicio.getDate() + i);

      const claveDelDia = this.obtenerClaveDia(fecha);
      const eventosDelDia = eventosPorDia.get(claveDelDia) || [];

      dias.push({
        fecha: fecha,
        esDelMesActual: fecha.getMonth() === month,
        esHoy: this.esMismaFecha(fecha, hoy),
        tieneEventos: eventosDelDia.length > 0,
        eventos: eventosDelDia
      });
    }

    return dias;
  }

  // Agrupar eventos por día
  private agruparEventosPorDia(eventos: CasacionDataModel[]): Map<string, CasacionDataModel[]> {
    const mapa = new Map<string, CasacionDataModel[]>();

    eventos.forEach(evento => {
      // Parsear la fecha del formato "2025-06-26T08:30:01"
      const fechaEvento = new Date(evento.fechaProgramacion);
      const claveDia = this.obtenerClaveDia(fechaEvento);

      if (!mapa.has(claveDia)) {
        mapa.set(claveDia, []);
      }
      mapa.get(claveDia)!.push(evento);
    });

    // Ordenar eventos dentro de cada día por fecha
    mapa.forEach((eventosDelDia) => {
      eventosDelDia.sort((a, b) => {
        return new Date(a.fechaProgramacion).getTime() - new Date(b.fechaProgramacion).getTime();
      });
    });

    return mapa;
  }

  // Obtener clave única para un día (formato: "YYYY-MM-DD")
  private obtenerClaveDia(fecha: Date): string {
    const year = fecha.getFullYear();
    const month = String(fecha.getMonth() + 1).padStart(2, '0');
    const day = String(fecha.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  // Verificar si dos fechas son el mismo día
  private esMismaFecha(fecha1: Date, fecha2: Date): boolean {
    return fecha1.getFullYear() === fecha2.getFullYear() &&
           fecha1.getMonth() === fecha2.getMonth() &&
           fecha1.getDate() === fecha2.getDate();
  }

  // Navegar al mes anterior
  previousMonth(): void {
    this.currentDate.update(date => {
      const newDate = new Date(date);
      newDate.setMonth(date.getMonth() - 1);
      return newDate;
    });
    this.cargarEventosCalendario();
  }

  // Navegar al mes siguiente
  nextMonth(): void {
    this.currentDate.update(date => {
      const newDate = new Date(date);
      newDate.setMonth(date.getMonth() + 1);
      return newDate;
    });
    this.cargarEventosCalendario();
  }

  // Ir a hoy
  goToToday(): void {
    this.currentDate.set(new Date());
    this.cargarEventosCalendario();
  }

  // Verificar si un día es del mes actual
  isCurrentMonth(date: Date): boolean {
    const dia = this.buscarDiaEnLista(date);
    return dia?.esDelMesActual ?? false;
  }

  // Verificar si un día es hoy
  isToday(date: Date): boolean {
    const dia = this.buscarDiaEnLista(date);
    return dia?.esHoy ?? false;
  }

  // Verificar si un día tiene eventos
  hasEvents(date: Date): boolean {
    const dia = this.buscarDiaEnLista(date);
    return dia?.tieneEventos ?? false;
  }

  // Obtener eventos de un día
  getEventsForDay(date: Date): CasacionDataModel[] {
    const dia = this.buscarDiaEnLista(date);
    return dia?.eventos ?? [];
  }

  // Buscar un día en la lista
  private buscarDiaEnLista(date: Date): DiaCalendario | undefined {
    return this.diasCalendario().find(dia => this.esMismaFecha(dia.fecha, date));
  }

  // Click en un día
  onDayClick(date: Date): void {
    const dia = this.buscarDiaEnLista(date);
    if (dia && dia.tieneEventos) {
      this.openDayOverlay(dia);
    }
  }

  // Abrir overlay con votaciones del día
  private openDayOverlay(dia: DiaCalendario): void {
    const dialogRef = this.dialog.open(CalendarOverlayComponent, {
      width: '800px',
      maxHeight: '80vh',
      data: {
        date: dia.fecha,
        eventos: dia.eventos
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.seleccionado) {
        //this.storeVotaciones.dispatch(actionsVotaciones.cargarDetalleCasacion({ detalle: nuevo }));
        //this.router.navigate(['/voting', result.votacionId]);
      }
    });
  }

  // Obtener clase CSS para el día
  getDayClass(date: Date): string {
    const classes = ['calendar-day'];
    const dia = this.buscarDiaEnLista(date);

    if (!dia) {
      return classes.join(' ');
    }

    if (!dia.esDelMesActual) {
      classes.push('other-month');
    }

    if (dia.esHoy) {
      classes.push('today');
    }

    if (dia.tieneEventos) {
      classes.push('has-events');
    }

    return classes.join(' ');
  }
}
