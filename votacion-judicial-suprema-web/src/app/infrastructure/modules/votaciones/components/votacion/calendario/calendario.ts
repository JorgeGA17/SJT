import {ChangeDetectionStrategy, Component, effect, model, signal} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {provideNativeDateAdapter} from '@angular/material/core';
import {MatDatepickerInputEvent, MatDatepickerModule} from '@angular/material/datepicker';
import { Router } from '@angular/router';
import { CalendarViewComponent } from "./calendar-view/calendar-view.component";
import { MatExpansionModule } from '@angular/material/expansion';

@Component({
  selector: 'app-calendario',
  templateUrl: './calendario.html',
  styleUrl: './calendario.scss',
  providers: [provideNativeDateAdapter()],
  imports: [MatCardModule, MatDatepickerModule, MatButtonModule, CalendarViewComponent, MatExpansionModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Calendario {
  selected = model<Date | null>(null);

  constructor(private route: Router, ) {
    effect(() => {
      const currentValue = this.selected();
      console.log('El valor cambió a:', currentValue);
      
      // Aquí puedes ejecutar cualquier lógica
      if (currentValue) {
        this.handleDateChange(currentValue);
      }
    });
  }

  private handleDateChange(date: Date) {
    this.route.navigate(['/votaciones/votacion/detalle']);
  }

  regresar():void {
    this.route.navigate(['/votaciones/menu']);
  }
  
}
