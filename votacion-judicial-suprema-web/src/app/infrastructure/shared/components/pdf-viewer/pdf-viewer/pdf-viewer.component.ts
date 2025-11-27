import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatMenuModule } from '@angular/material/menu';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { Documento } from '../../../../modules/votaciones/components/votacion/calendario/calendar-view/votacion.model';

type SplitDirection = 'horizontal' | 'vertical';

@Component({
  selector: 'app-pdf-viewer',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatMenuModule,
    PdfViewerModule
  ],
  templateUrl: './pdf-viewer.component.html',
  styleUrls: ['./pdf-viewer.component.scss']
})
export class PdfViewerComponent implements OnInit {
  @Input() documento!: Documento;
  @Input() panelId?: string;
  @Output() splitDocument = new EventEmitter<{ panelId: string; direction: SplitDirection; documento: Documento }>();

  ngOnInit(): void {
    // Inicialización básica si es necesaria
  }

  // Método para dividir el documento actual
  onSplitDocument(direction: SplitDirection): void {
    if (this.panelId && this.documento) {
      this.splitDocument.emit({
        panelId: this.panelId,
        direction,
        documento: this.documento
      });
    }
  }
}
