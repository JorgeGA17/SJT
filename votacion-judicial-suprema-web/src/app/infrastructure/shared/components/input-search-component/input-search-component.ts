import { CommonModule } from '@angular/common';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-input-search-component',
  imports: [CommonModule,
    FormsModule,
    MatFormFieldModule, 
    MatInputModule, 
    MatIconModule,
    MatButtonModule],
  templateUrl: './input-search-component.html',
  styleUrl: './input-search-component.scss'
})
export class InputSearchComponent implements OnInit{
  @Input() placeholder: string = 'Ingrese su búsqueda...';
  @Input() label: string = 'Buscar';
  @Input() searchValue: string = '';
  @Input() disabled: boolean = false;
  
  @Output() search = new EventEmitter<string>();
  @Output() searchValueChange = new EventEmitter<string>();
  @Output() clear = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  onSearch(): void {
    if (this.searchValue && this.searchValue.trim()) {
      this.search.emit(this.searchValue.trim());
      console.log('Búsqueda realizada:', this.searchValue);
    }
  }

  onClear(): void {
    this.searchValue = '';
    this.searchValueChange.emit(this.searchValue);
    this.clear.emit();
    console.log('Búsqueda limpiada');
  }

  onInputChange(): void {
    this.searchValueChange.emit(this.searchValue);
  }
}
