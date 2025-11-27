import { Component, Input, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdkDragDrop, DragDropModule, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-pick-list',
  standalone: true,
  imports: [
    CommonModule,
    DragDropModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule
  ],
  templateUrl: './pick-list.component.html',
  styleUrls: ['./pick-list.component.scss']
})
export class PickListComponent implements OnInit {
  @Input() availableItems: string[] = [];
  @Input() selectedItems: string[] = [];

  // Signals para las listas
  disponiblesSignal = signal<string[]>([]);
  seleccionadosSignal = signal<string[]>([]);

  // Signals para items seleccionados en cada lista
  selectedDisponibles = signal<Set<string>>(new Set());
  selectedSeleccionados = signal<Set<string>>(new Set());

  ngOnInit(): void {
    this.disponiblesSignal.set([...this.availableItems]);
    this.seleccionadosSignal.set([...this.selectedItems]);
  }

  // Drag & Drop entre listas
  drop(event: CdkDragDrop<string[]>): void {
    if (event.previousContainer === event.container) {
      // Reordenar dentro de la misma lista
      const items = [...event.container.data];
      moveItemInArray(items, event.previousIndex, event.currentIndex);

      if (event.container.id === 'disponibles-list') {
        this.disponiblesSignal.set(items);
      } else {
        this.seleccionadosSignal.set(items);
      }
    } else {
      // Transferir entre listas
      const sourceItems = [...event.previousContainer.data];
      const destItems = [...event.container.data];

      transferArrayItem(
        sourceItems,
        destItems,
        event.previousIndex,
        event.currentIndex
      );

      if (event.previousContainer.id === 'disponibles-list') {
        this.disponiblesSignal.set(sourceItems);
        this.seleccionadosSignal.set(destItems);
      } else {
        this.seleccionadosSignal.set(sourceItems);
        this.disponiblesSignal.set(destItems);
      }
    }

    // Limpiar selecciones
    this.clearSelections();
  }

  // Toggle selección de item
  toggleSelection(item: string, lista: 'disponibles' | 'seleccionados'): void {
    if (lista === 'disponibles') {
      this.selectedDisponibles.update(set => {
        const newSet = new Set(set);
        if (newSet.has(item)) {
          newSet.delete(item);
        } else {
          newSet.add(item);
        }
        return newSet;
      });
    } else {
      this.selectedSeleccionados.update(set => {
        const newSet = new Set(set);
        if (newSet.has(item)) {
          newSet.delete(item);
        } else {
          newSet.add(item);
        }
        return newSet;
      });
    }
  }

  // Verificar si un item está seleccionado
  isSelected(item: string, lista: 'disponibles' | 'seleccionados'): boolean {
    return lista === 'disponibles'
      ? this.selectedDisponibles().has(item)
      : this.selectedSeleccionados().has(item);
  }

  // Mover items seleccionados de disponibles a seleccionados
  moveToSelected(): void {
    const itemsToMove = Array.from(this.selectedDisponibles());
    if (itemsToMove.length === 0) return;

    this.disponiblesSignal.update(items =>
      items.filter(item => !itemsToMove.includes(item))
    );

    this.seleccionadosSignal.update(items => [...items, ...itemsToMove]);

    this.clearSelections();
  }

  // Mover items seleccionados de seleccionados a disponibles
  moveToAvailable(): void {
    const itemsToMove = Array.from(this.selectedSeleccionados());
    if (itemsToMove.length === 0) return;

    this.seleccionadosSignal.update(items =>
      items.filter(item => !itemsToMove.includes(item))
    );

    this.disponiblesSignal.update(items => [...items, ...itemsToMove]);

    this.clearSelections();
  }

  // Mover todos los items a seleccionados
  moveAllToSelected(): void {
    this.seleccionadosSignal.update(items => [
      ...items,
      ...this.disponiblesSignal()
    ]);
    this.disponiblesSignal.set([]);
    this.clearSelections();
  }

  // Mover todos los items a disponibles
  moveAllToAvailable(): void {
    this.disponiblesSignal.update(items => [
      ...items,
      ...this.seleccionadosSignal()
    ]);
    this.seleccionadosSignal.set([]);
    this.clearSelections();
  }

  // Limpiar todas las selecciones
  clearSelections(): void {
    this.selectedDisponibles.set(new Set());
    this.selectedSeleccionados.set(new Set());
  }

  // Obtener valores actuales
  getSelectedItems(): string[] {
    return this.seleccionadosSignal();
  }

  getAvailableItems(): string[] {
    return this.disponiblesSignal();
  }
}
