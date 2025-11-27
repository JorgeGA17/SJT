import { Component, Input, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';

import { PartsTableComponent } from '../parts-table/parts-table.component';
import { PickListComponent } from '../pick-list/pick-list.component';
import { RichTextEditorComponent } from '../rich-text-editor/rich-text-editor.component';
import { Votacion } from '../../calendario/calendar-view/votacion.model';
import { PdfViewerDialogComponent } from '../../../../../../shared/components/pdf-viewer/pdf-viewer-dialog/pdf-viewer-dialog.component';
import { CasacionDataModel } from '../../../../../../../domain/models/Casacion.model';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import {MatRadioModule} from '@angular/material/radio';
import { InputSearchComponent } from '../../../../../../shared/components/input-search-component/input-search-component';
import { MatDividerModule } from '@angular/material/divider';
import { MatCheckboxModule } from '@angular/material/checkbox';


@Component({
  selector: 'app-voting-content',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    PartsTableComponent,
    PickListComponent,
    RichTextEditorComponent,
    MatSelectModule,
    MatIconModule,
    MatCardModule,
    MatExpansionModule,
    MatRadioModule,
    InputSearchComponent,
    MatDividerModule,
    MatCheckboxModule
  ],
  templateUrl: './voting-content.component.html',
  styleUrls: ['./voting-content.component.scss']
})
export class VotingContentComponent {
  @Input() votacion: CasacionDataModel | null = null;

  constructor(private dialog: MatDialog) {}

  // Abrir visualizador de PDFs
  openPdfViewer(): void {
    /*if (!this.votacion || !this.votacion.documentos || this.votacion.documentos.length === 0) {
      return;
    }*/

    /*this.dialog.open(PdfViewerDialogComponent, {
      width: '90vw',
      height: '90vh',
      maxWidth: '100vw',
      maxHeight: '100vh',
      panelClass: ['pdf-dialog-panel', 'resizable-dialog'],
      hasBackdrop: false, // No modal - permite interactuar con el fondo
      disableClose: false,
      position: {
        top: '5vh',
        left: '5vw'
      },
      data: {
        documentos: this.votacion.documentos
      }
    });*/
  }

  // Formatear fecha
  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('es-ES', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }
}
