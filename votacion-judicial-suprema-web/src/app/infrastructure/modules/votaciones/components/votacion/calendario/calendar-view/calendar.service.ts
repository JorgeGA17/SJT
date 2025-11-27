import { Injectable, signal, computed } from '@angular/core';
import { CalendarEvent, Votacion } from './votacion.model';
import { VotingService } from './voting.service';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {
  private selectedDateSignal = signal<Date>(new Date());

  readonly selectedDate = this.selectedDateSignal.asReadonly();

  constructor(private votingService: VotingService) {}

  // Computed signal para eventos del mes actual
  readonly currentMonthEvents = computed(() => {
    const selectedDate = this.selectedDateSignal();
    const startOfMonth = new Date(selectedDate.getFullYear(), selectedDate.getMonth(), 1);
    const endOfMonth = new Date(selectedDate.getFullYear(), selectedDate.getMonth() + 1, 0);

    return this.getEventsForDateRange(startOfMonth, endOfMonth);
  });

  // Seleccionar fecha
  selectDate(date: Date): void {
    this.selectedDateSignal.set(date);
  }

  // Obtener eventos para un día específico
  getEventsForDate(date: Date): Votacion[] {
    return this.votingService.getVotacionesByDate(date);
  }

  // Obtener eventos para un rango de fechas
  getEventsForDateRange(startDate: Date, endDate: Date): CalendarEvent[] {
    const votaciones = this.votingService.getVotacionesByDateRange(startDate, endDate);
    const eventMap = new Map<string, Votacion[]>();

    // Agrupar votaciones por fecha
    votaciones.forEach(votacion => {
      const dateKey = this.getDateKey(votacion.fecha);
      if (!eventMap.has(dateKey)) {
        eventMap.set(dateKey, []);
      }
      eventMap.get(dateKey)!.push(votacion);
    });

    // Convertir a array de CalendarEvent
    const events: CalendarEvent[] = [];
    eventMap.forEach((votaciones, dateKey) => {
      const [year, month, day] = dateKey.split('-').map(Number);
      events.push({
        date: new Date(year, month, day),
        votaciones: votaciones.sort((a, b) => a.expediente.localeCompare(b.expediente))
      });
    });

    return events.sort((a, b) => a.date.getTime() - b.date.getTime());
  }

  // Verificar si una fecha tiene eventos
  hasEvents(date: Date): boolean {
    return this.getEventsForDate(date).length > 0;
  }

  // Obtener cantidad de eventos para una fecha
  getEventCount(date: Date): number {
    return this.getEventsForDate(date).length;
  }

  // Cambiar mes
  changeMonth(offset: number): void {
    this.selectedDateSignal.update(date => {
      const newDate = new Date(date);
      newDate.setMonth(date.getMonth() + offset);
      return newDate;
    });
  }

  // Ir a mes específico
  goToMonth(year: number, month: number): void {
    this.selectedDateSignal.update(date => {
      const newDate = new Date(date);
      newDate.setFullYear(year);
      newDate.setMonth(month);
      return newDate;
    });
  }

  // Ir a hoy
  goToToday(): void {
    this.selectedDateSignal.set(new Date());
  }

  // Utilidad: obtener key de fecha para el mapa
  private getDateKey(date: Date): string {
    return `${date.getFullYear()}-${date.getMonth()}-${date.getDate()}`;
  }

  // Utilidad: obtener días del mes en formato de calendario (con días del mes anterior y siguiente)
  getCalendarDays(year: number, month: number): Date[] {
    const days: Date[] = [];
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const startDayOfWeek = firstDay.getDay(); // 0 = domingo, 1 = lunes, etc.

    // Agregar días del mes anterior para completar la primera semana
    const prevMonthLastDay = new Date(year, month, 0);
    for (let i = startDayOfWeek - 1; i >= 0; i--) {
      const day = new Date(year, month - 1, prevMonthLastDay.getDate() - i);
      days.push(day);
    }

    // Agregar días del mes actual
    for (let i = 1; i <= lastDay.getDate(); i++) {
      days.push(new Date(year, month, i));
    }

    // Agregar días del mes siguiente para completar la última semana
    const remainingDays = 42 - days.length; // 6 semanas * 7 días
    for (let i = 1; i <= remainingDays; i++) {
      days.push(new Date(year, month + 1, i));
    }

    return days;
  }
}
