import { Component, Input, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { CdkTextareaAutosize, TextFieldModule } from '@angular/cdk/text-field';
import { Parte } from '../../calendario/calendar-view/votacion.model';

@Component({
  selector: 'app-parts-table',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatTableModule,
    MatSelectModule,
    MatChipsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    TextFieldModule
  ],
  templateUrl: './parts-table.component.html',
  styleUrls: ['./parts-table.component.scss']
})
export class PartsTableComponent implements OnInit {
  @Input() partes: Parte[] = [
    {tipoParte: "DTE",
      nombre: "RDGRWE GERG, ERGERGERG",
      sentido: "UNANIMIDAD",
      fallo: "FUNDADO EN PARTE",
      discordia:[],
      efectosSentido:""
    }
  ];

  displayedColumns: string[] = [
    'tipoParte',
    'nombre',
    'sentido',
    'fallo',
    'discordia',
    'efectosSentido'
  ];

  partesForm!: FormGroup;
  expandedRows = signal<Set<number>>(new Set());

  // Opciones para los selects
  sentidoOptions = ['Favorable', 'Desfavorable', 'Neutral', 'Abstención'];
  falloOptions = ['Procedente', 'Improcedente', 'Parcialmente procedente', 'Sin materia'];

  // Magistrados disponibles para discordia
  magistradosDisponibles = [
    'Magistrado Juan Pérez González',
    'Magistrada María López Rodríguez',
    'Magistrado Carlos Sánchez Martínez',
    'Magistrada Ana García Fernández',
    'Magistrado Luis Martínez Jiménez'
  ];

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.partesForm = this.fb.group({
      partes: this.fb.array(
        this.partes.map(parte => this.createParteFormGroup(parte))
      )
    });
  }

  private createParteFormGroup(parte: Parte): FormGroup {
    return this.fb.group({
      tipoParte: [parte.tipoParte],
      nombre: [parte.nombre],
      sentido: [parte.sentido],
      fallo: [parte.fallo],
      discordia: [parte.discordia || []],
      efectosSentido: [parte.efectosSentido]
    });
  }

  get partesFormArray(): FormArray {
    return this.partesForm.get('partes') as FormArray;
  }

  getParteFormGroup(index: number): FormGroup {
    return this.partesFormArray.at(index) as FormGroup;
  }

  // Toggle expansión de fila para efectos del sentido
  toggleRow(index: number): void {
    this.expandedRows.update(rows => {
      const newRows = new Set(rows);
      if (newRows.has(index)) {
        newRows.delete(index);
      } else {
        newRows.add(index);
      }
      return newRows;
    });
  }

  isRowExpanded(index: number): boolean {
    return this.expandedRows().has(index);
  }

  // Agregar magistrado a discordia
  addMagistrado(index: number, magistrado: string): void {
    const discordiaControl = this.getParteFormGroup(index).get('discordia');
    const currentDiscordia = discordiaControl?.value || [];

    if (!currentDiscordia.includes(magistrado)) {
      discordiaControl?.setValue([...currentDiscordia, magistrado]);
    }
  }

  // Remover magistrado de discordia
  removeMagistrado(index: number, magistrado: string): void {
    const discordiaControl = this.getParteFormGroup(index).get('discordia');
    const currentDiscordia = discordiaControl?.value || [];

    discordiaControl?.setValue(
      currentDiscordia.filter((m: string) => m !== magistrado)
    );
  }

  // Obtener magistrados disponibles que no están en discordia
  getAvailableMagistrados(index: number): string[] {
    const currentDiscordia = this.getParteFormGroup(index).get('discordia')?.value || [];
    return this.magistradosDisponibles.filter(m => !currentDiscordia.includes(m));
  }

  // Obtener los datos actualizados
  getFormValue(): Parte[] {
    return this.partesFormArray.value;
  }

  // Track by para optimización
  trackByIndex(index: number): number {
    return index;
  }
}
