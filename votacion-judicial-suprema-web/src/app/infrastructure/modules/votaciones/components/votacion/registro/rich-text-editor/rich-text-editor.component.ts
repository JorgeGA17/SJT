import { Component, Input, Output, EventEmitter, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { QuillModule } from 'ngx-quill';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-rich-text-editor',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    QuillModule,
    MatCardModule
  ],
  templateUrl: './rich-text-editor.component.html',
  styleUrls: ['./rich-text-editor.component.scss']
})
export class RichTextEditorComponent implements OnInit {
  @Input() initialContent: string = '';
  @Output() contentChange = new EventEmitter<string>();

  contentSignal = signal<string>('');

  // Configuración del editor Quill
  quillConfig = {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'],
      //['blockquote', 'code-block'],
      //[{ 'header': 1 }, { 'header': 2 }],
      //[{ 'list': 'ordered'}, { 'list': 'bullet' }],
      //[{ 'script': 'sub'}, { 'script': 'super' }],
      //[{ 'indent': '-1'}, { 'indent': '+1' }],
      //[{ 'direction': 'rtl' }],
      //[{ 'size': ['small', false, 'large', 'huge'] }],
      //[{ 'header': [1, 2, 3, 4, 5, 6, false] }],
      [{ 'color': [] }, { 'background': [] }],
      //[{ 'font': [] }],
      //[{ 'align': [] }],
      ['clean'],
      //['link', 'image', 'video']
    ]
  };

  ngOnInit(): void {
    this.contentSignal.set(this.initialContent);
  }

  // Evento cuando cambia el contenido
  onContentChanged(event: any): void {
    const content = event.html || '';
    this.contentSignal.set(content);
    this.contentChange.emit(content);
  }

  // Obtener contenido actual
  getContent(): string {
    return this.contentSignal();
  }

  // Establecer contenido
  setContent(content: string): void {
    this.contentSignal.set(content);
  }

  // Limpiar contenido
  clearContent(): void {
    this.contentSignal.set('');
    this.contentChange.emit('');
  }
}
