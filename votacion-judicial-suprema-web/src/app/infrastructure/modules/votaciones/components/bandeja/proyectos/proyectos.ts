import { AsyncPipe, CommonModule, DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatRippleModule, provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-proyectos',
  imports: [CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule, 
    MatInputModule, 
    MatSelectModule,
    MatIconModule, 
    MatButtonModule,
    AsyncPipe,
    MatDatepickerModule, 
    MatRippleModule],
   providers:[provideNativeDateAdapter(), DatePipe],
  templateUrl: './proyectos.html',
  styleUrl: './proyectos.scss'
})
export class Proyectos {
  fechaIniSeleccionado: Date = new Date(); 
  fechaFinSeleccionado: Date = new Date(); 
}
