import { Component, Output, EventEmitter, computed, signal, effect, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatListModule } from '@angular/material/list';
import { VotingService } from '../../calendario/calendar-view/voting.service';
import { CalendarService } from '../../calendario/calendar-view/calendar.service';
import { Votacion } from '../../calendario/calendar-view/votacion.model';


@Component({
  selector: 'app-voting-navigation',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatListModule
  ],
  templateUrl: './voting-navigation.component.html',
  styleUrls: ['./voting-navigation.component.scss']
})
export class VotingNavigationComponent {
  @Output() votacionSelected = new EventEmitter<string>();

  // Inyección de dependencias
  public votingService = inject(VotingService);
  public calendarService = inject(CalendarService);

  selectedDate = this.calendarService.selectedDate;
  selectedVotacionId = this.votingService.selectedVotacionId;

  // Computed para votaciones del día seleccionado
  votacionesDelDia = computed(() => {
    const date = this.selectedDate();
    return this.calendarService.getEventsForDate(date);
  });

  // Evento cuando cambia la fecha en el datepicker
  onDateChange(date: Date | null): void {
    if (date) {
      this.calendarService.selectDate(date);
    }
  }

  // Seleccionar votación
  selectVotacion(votacion: Votacion): void {
    this.votingService.selectVotacion(votacion.id);
    this.votacionSelected.emit(votacion.id);
  }

  // Verificar si una votación está seleccionada
  isSelected(votacion: Votacion): boolean {
    return this.selectedVotacionId() === votacion.id;
  }

  // Filter para el datepicker (marcar días con eventos)
  dateFilter = (date: Date | null): boolean => {
    if (!date) return false;
    return this.calendarService.hasEvents(date);
  };

  // Clase personalizada para fechas con eventos
  dateClass = (date: Date): string => {
    return this.calendarService.hasEvents(date) ? 'has-events-date' : '';
  };
}
