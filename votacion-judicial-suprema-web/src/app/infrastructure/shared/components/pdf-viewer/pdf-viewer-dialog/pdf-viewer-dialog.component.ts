import { Component, Inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { PdfFileListComponent } from '../pdf-file-list/pdf-file-list.component';
import { PdfMultiPanelComponent } from '../pdf-multi-panel/pdf-multi-panel.component';
import { DraggableDialogDirective } from '../directives/draggable-dialog.directive';
import { ResizableDialogDirective } from '../directives/resizable-dialog.directive';
import { Documento } from '../../../../modules/votaciones/components/votacion/calendario/calendar-view/votacion.model';

@Component({
  selector: 'app-pdf-viewer-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    PdfFileListComponent,
    PdfMultiPanelComponent,
    DraggableDialogDirective,
    ResizableDialogDirective
  ],
  templateUrl: './pdf-viewer-dialog.component.html',
  styleUrls: ['./pdf-viewer-dialog.component.scss']
})
export class PdfViewerDialogComponent {
  documentos: Documento[] = [];
  sidebarOpen = signal<boolean>(true);
  openDocuments = signal<Documento[]>([]);

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { documentos: Documento[] },
    private dialogRef: MatDialogRef<PdfViewerDialogComponent>
  ) {
    this.documentos = data.documentos || [];
    // Abrir el primer documento por defecto
    if (this.documentos.length > 0) {
      this.openDocuments.set([this.documentos[0]]);
    }
  }

  toggleSidebar(): void {
    this.sidebarOpen.update(open => !open);
  }

  onDocumentSelected(documento: Documento): void {
    // Si el documento ya está abierto, no hacer nada
    const isOpen = this.openDocuments().some(doc => doc.id === documento.id);
    if (!isOpen) {
      this.openDocuments.update(docs => [...docs, documento]);
    }
  }

  onDocumentClosed(documento: Documento): void {
    this.openDocuments.update(docs => docs.filter(doc => doc.id !== documento.id));
  }

  close(): void {
    this.dialogRef.close();
  }
}
