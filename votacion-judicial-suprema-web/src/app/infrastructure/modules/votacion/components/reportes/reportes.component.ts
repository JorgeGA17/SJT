import { AfterViewInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AppVJState } from '../../../../../infrastructure/global-store/vj.reducers';
import { Store } from '@ngrx/store';
import Swal from 'sweetalert2';
import * as actions from '../../../../../infrastructure/global-store/vj.actions';
import { constantes } from 'src/app/constants';
import { ProyectosService } from 'src/app/infrastructure/services/remote/casacion/proyectos.service';
import { EstadoModel, ReporteModel } from 'src/app/domain/models/Proyectos.model';
import { SelectItem } from 'primeng/api';
import { LoginService } from 'src/app/infrastructure/services/remote/autenticacion/login.service';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.scss']
})
export class ReportesComponent implements OnInit, AfterViewInit {

  listaEstadoProye : EstadoModel[];
  estadoProySeleccionado : EstadoModel;

  listaSoloPendEstadoProye : EstadoModel[];

  listaEstadoVot : EstadoModel[];
  estadoVotSeleccionado : EstadoModel;

  listaResponsables : EstadoModel[];
  responsableSeleccionado : EstadoModel;

  listaReportes: SelectItem<string>[];
  reporteSeleccionado: SelectItem<string>;

  fechaInicioSel: Date | null = null;
  fechaFinSel: Date | null = null;

  filtroFechaInicio: boolean = false;
  filtroFechaFin: boolean = false;
  filtroResponsable: boolean = false;
  filtroEstadoVot: boolean = false;
  filtroEstadoProy: boolean = false;

  flagSoloPendiente: boolean = false;

  dialogResultado : boolean = false;
  listaResultado : ReporteModel[]= [];

  minFechaFinal: Date;
  maxFechaInicial: Date;

  private distritoUsuario: string = '';
  private provinciaUsuario: string = '';
  private salaUsuario: string = '';
  nombreSalaUsuario: string = '';

  colExpediente: boolean = true;
  colProgramacion: boolean = true;
  colPonente: boolean = true;
  colEstadoVot: boolean = true;
  colTipoRec: boolean = true;
  colRec: boolean = true;
  colSentido: boolean = true;
  colFallo: boolean = true;
  colResponsable: boolean = true;
  colEstadoProy: boolean = true;
  colFechaEnvio: boolean = true;
  colPendientesValidar: boolean = true;

  constructor(private router: Router, private proyectosService: ProyectosService, private store: Store<AppVJState>, private cdr: ChangeDetectorRef,private loginService: LoginService,){

      this.distritoUsuario = this.loginService.getDistrito();
      this.provinciaUsuario = this.loginService.getProvincia();
      this.salaUsuario = this.loginService.getSala();
      this.nombreSalaUsuario = this.loginService.getNombreSala();

      this.listaReportes = [
        {value: '1', label: 'Reporte de estado de votaciones'},
        {value: '2', label: 'Reporte de estado de proyectos'}
      ];
      this.reporteSeleccionado = this.listaReportes[0];

      this.listaEstadoProye = [];
      this.estadoProySeleccionado = { code:'',name:'',activo: ''};

      this.listaEstadoVot = [];
      this.estadoVotSeleccionado = { code:'',name:'',activo: ''};
      
      this.listaResponsables = [];
      this.responsableSeleccionado = { code:'',name:'',activo: ''};

      this.minFechaFinal = new Date(2000, 1, 1, 0, 0, 0);
      this.maxFechaInicial = new Date();

      this.listaSoloPendEstadoProye = [{code: '1', name:'PENDIENTE', activo: 'S'}]
    }

    ngOnInit(): void {
      this.cargaInicial();
    }

    ngAfterViewInit(): void {
      this.cargarConfiguracionReporte();
      this.cdr.detectChanges();
    }

    cargaInicial(): void {
      this.listarEstadosProy();
      this.listarEstadosVot();
      this.listarResponsables();
    }


    cargarConfiguracionReporte(): void {
      this.limpiarFiltros();
      switch(this.reporteSeleccionado.value){
        case '1':
          this.filtroFechaInicio = true;
          this.filtroFechaFin = true;
          //this.filtroResponsable = true;
          this.filtroEstadoVot = true;
          //this.filtroEstadoProy = true;
          this.responsableSeleccionado = { code: '', name: 'TODOS', activo: 'S' };
          this.estadoProySeleccionado = { code: '0', name: 'TODOS', activo: 'S' };

          this.colExpediente = true;
          this.colProgramacion = true;
          this.colPonente = true;
          this.colEstadoVot = true;
          this.colRec = true;
          this.colSentido = true;
          this.colFallo = true;
          this.colResponsable = false;
          this.colEstadoProy = false;
          this.colFechaEnvio = false;
          this.colPendientesValidar = false;

          break;
        case '2':
          this.filtroFechaInicio = true;
          this.filtroFechaFin = true;
          this.filtroResponsable = true;
          this.filtroEstadoVot = true;
          this.filtroEstadoProy = true;

          this.colExpediente = true;
          this.colProgramacion = true;
          this.colPonente = true;
          this.colEstadoVot = true;
          this.colRec = true;
          this.colSentido = true;
          this.colFallo = true;
          this.colResponsable = true;
          this.colEstadoProy = true;
          this.colFechaEnvio = true;
          this.colPendientesValidar = true;          

          break;        
      }
    }

    limpiarFiltros(): void{
      this.filtroFechaInicio = false;
      this.filtroFechaFin = false;
      this.filtroResponsable = false;
      this.filtroEstadoVot = false;
      this.filtroEstadoProy = false;
    }

    listarEstadosProy(): void {
      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.proyectosService.obtenerEstadosProyectos().subscribe({
        next:(response:any)=>{
          this.listaEstadoProye = [{code: '0', name:'TODOS', activo: 'S'}];
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              for(let rs of response.data){
                if(rs.code != '4'){
                  this.listaEstadoProye.push(rs);
                }
              }
            }
            else{
              Swal.fire('¡Atención!', "Listar estados de proyecto: Error de respuesta", 'warning');
            }
          }
          else{
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });
    }

    listarEstadosVot(): void {
      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.proyectosService.obtenerEstadosVot().subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            this.listaEstadoVot = [{code: '', name:'TODOS', activo: 'S'}];
            if(response.data){
              for( let rs of response.data){
                this.listaEstadoVot.push(rs);
              }
            }
            else{
              Swal.fire('¡Atención!', "Listar estados de votación: Error de respuesta", 'warning');
            }
          }
          else{
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });
    }
    
    listarResponsables(): void {
      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.proyectosService.obtenerResponsablesVot().subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            this.listaResponsables = [{code: '', name:'TODOS', activo: 'S'}];
            if(response.data){
              for( let rs of response.data){
                this.listaResponsables.push(rs);
              }
            }
            else{
              Swal.fire('¡Atención!', "Listar responsables: Error de respuesta", 'warning');
            }
          }
          else{
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });
    }

  descargarReporte():void{
    if(this.validarParams()){
      let excelBase64 : string = "";
      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.proyectosService.descargarReporte(this.distritoUsuario,this.provinciaUsuario,this.salaUsuario,this.responsableSeleccionado.code,this.estadoProySeleccionado.code,this.estadoVotSeleccionado.code,this.fechaInicioSel?this.fechaInicioSel.toString():null,this.fechaFinSel?this.fechaFinSel.toString():null,this.reporteSeleccionado.value).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              excelBase64 = response.data;
              if(excelBase64){
                // Convertir la cadena Base64 a un Blob
                const byteCharacters = atob(excelBase64);
                const byteNumbers = new Array(byteCharacters.length);
                for (let i = 0; i < byteCharacters.length; i++) {
                  byteNumbers[i] = byteCharacters.charCodeAt(i);
                }
                const byteArray = new Uint8Array(byteNumbers);
                const blob = new Blob([byteArray], { type: 'xlsx' });
  
                // Crear un enlace de descarga
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'reporte.xlsx';
                a.click();
  
                // Limpiar el objeto URL para liberar memoria
                window.URL.revokeObjectURL(url);
              }                
            }
            else{
              Swal.fire('¡Atención!', "Descargar reporte: Error de respuesta", 'warning');
            }
          }
          else{
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{

          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },      
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });        
    }
  }

  verResultado(): void {
    
    if(this.validarParams()){
      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.proyectosService.visualizarReporte(this.distritoUsuario,this.provinciaUsuario,this.salaUsuario,this.responsableSeleccionado.code,this.estadoProySeleccionado.code,this.estadoVotSeleccionado.code,this.fechaInicioSel?this.fechaInicioSel.toString():null,this.fechaFinSel?this.fechaFinSel.toString():null,this.reporteSeleccionado.value).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              this.listaResultado = this.agregarClaveDeGrupo(response.data);
              console.log(this.listaResultado);
              //this.listaResultado = response.data;
              //this.listaResultado.sort((a, b) => parseInt(b.id) - parseInt(a.id));
              this.dialogResultado =true;
            }
            else{
              Swal.fire('¡Atención!', "Visualizar reporte: Error de respuesta", 'warning');
            }
          }
          else{
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{

          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },      
        error:(err)=>{
          this.dialogResultado =false;
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });        
    }
  }

  onDialogHide(){
    this.dialogResultado = false;
    this.listaResultado = [];
  }
  
  validarParams(): boolean {
    let val = true;
    let fechaSinTiempo = (fecha: Date): Date => {
      const [day, month, year] = fecha.toString().split('/').map(Number);  
      return new Date(year, month - 1, day); 
    }
    if (!this.fechaInicioSel || !this.fechaFinSel) {
      Swal.fire('¡Atención!', 'La fecha inicial y la fecha final del rango de búsqueda es obligatoria', 'info');
      return false;
    } else {
      let fechaI = fechaSinTiempo(this.fechaInicioSel);
      let fechaF = fechaSinTiempo(this.fechaFinSel);
      if (fechaI.getTime() > fechaF.getTime()) {
        Swal.fire('¡Atención!', 'La fecha final no puede ser menor a la fecha inicial del rango de búsqueda', 'info');
        return false;
      }
    }
    if (!this.responsableSeleccionado || !this.responsableSeleccionado.name) {
      Swal.fire('¡Atención!', 'Debe seleccionar un responsable', 'info');
      return false;
    }
    if (!this.estadoVotSeleccionado || !this.estadoVotSeleccionado.name) {
      Swal.fire('¡Atención!', 'Debe seleccionar un estado de votación', 'info');
      return false;
    }
    if (!this.estadoProySeleccionado || !this.estadoProySeleccionado.name) {
      Swal.fire('¡Atención!', 'Debe seleccionar un estado del proyecto', 'info');
      return false;
    }
    return val;
  }

  cambiarSel(objeto : any) : void {
    if(objeto && objeto.code == '900'){
      this.flagSoloPendiente = true;
      this.estadoProySeleccionado = this.listaSoloPendEstadoProye[0];
    } else {
      this.flagSoloPendiente = false;
    }
  }

  private agregarClaveDeGrupo(votos: any[]): any[] {
  return votos.map(v => ({
    ...v,
    grupoKey: this.generarGrupoKey(v)
  }));
}

private generarGrupoKey(v: any): string {
  // Aquí puedes personalizar los campos que definen tu grupo
  return `${v.numeroExpediente}|${v.fechaProgramacion}|${v.ponente}|${v.estadoVoto}`;
}

  back() :void {
    this.router.navigate(['/votacion/main']);
  }
}
