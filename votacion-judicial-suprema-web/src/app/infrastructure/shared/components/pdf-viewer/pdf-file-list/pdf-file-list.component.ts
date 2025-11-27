import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Documento } from '../../../../modules/votaciones/components/votacion/calendario/calendar-view/votacion.model';

@Component({
  selector: 'app-pdf-file-list',
  standalone: true,
  imports: [
    CommonModule,
    MatListModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './pdf-file-list.component.html',
  styleUrls: ['./pdf-file-list.component.scss']
})
export class PdfFileListComponent {
  @Input() documentos: Documento[] = [];
  @Input() openDocuments: Documento[] = [];
  @Output() documentSelected = new EventEmitter<Documento>();

  selectDocument(documento: Documento): void {
    this.documentSelected.emit(documento);
  }

  isDocumentOpen(documento: Documento): boolean {
    return this.openDocuments.some(doc => doc.id === documento.id);
  }
}
