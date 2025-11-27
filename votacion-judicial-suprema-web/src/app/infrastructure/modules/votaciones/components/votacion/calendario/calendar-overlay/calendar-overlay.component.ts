import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CasacionDataModel } from '../../../../../../../domain/models/Casacion.model';
import { AppVjState } from '../../../../../../global-store/vj.reducers';
import { Store } from '@ngrx/store';
import * as actions from '../../../../../../global-store/vj.actions';
import { AppVotacionesState } from '../../../../store/votaciones.reducers';
import * as actionsVotaciones from '../../../../store/actions';
import { Router } from '@angular/router';

@Component({
  selector: 'app-calendar-overlay',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './calendar-overlay.component.html',
  styleUrls: ['./calendar-overlay.component.scss']
})
export class CalendarOverlayComponent {
  votaciones: CasacionDataModel[] = [];
  selectedDate: Date;
  displayedColumns: string[] = ['numeroRecurso', 'fechaProgramacion', 'vocalPonente','nombreEstado', 'accion'];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { date: Date, eventos: CasacionDataModel[]},
    private dialogRef: MatDialogRef<CalendarOverlayComponent>,
    private router: Router,
    private store: Store<AppVjState>,
    private storeVotaciones: Store<AppVotacionesState>
  ) {
    this.selectedDate = data.date;
    this.votaciones = data.eventos;
  }

  // Formatear fecha para mostrar
  get formattedDate(): string {
    return this.selectedDate.toLocaleDateString('es-ES', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }

  // Seleccionar votación y cerrar dialog
  selectVotacion(votacion: CasacionDataModel): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.storeVotaciones.dispatch(actionsVotaciones.cargarDetalleCasacion({ detalle: votacion }));
    this.router.navigate(['/votaciones/votacion/detalle']);
    this.dialogRef.close({ seleccionado: true});
  }

  // Cerrar dialog sin selección
  close(): void {
    this.dialogRef.close();
  }

  // Formatear hora (si se necesita)
  formatTime(date: Date): string {
    return date.toLocaleTimeString('es-ES', {
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}
