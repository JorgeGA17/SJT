import { Component, Input, Output, EventEmitter, signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { PdfViewerComponent } from '../pdf-viewer/pdf-viewer.component';
import { Documento } from '../../../../modules/votaciones/components/votacion/calendario/calendar-view/votacion.model';

// Tipos para el sistema de paneles
type SplitDirection = 'horizontal' | 'vertical';

interface PanelLeaf {
  type: 'leaf';
  id: string;
  documento: Documento | null; // Null = panel vacío
}

interface PanelSplit {
  type: 'split';
  id: string;
  direction: SplitDirection;
  children: PanelNode[];
  sizes: number[]; // Porcentajes de cada hijo
}

type PanelNode = PanelLeaf | PanelSplit;

@Component({
  selector: 'app-pdf-multi-panel',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatTooltipModule,
    MatSnackBarModule,
    PdfViewerComponent
  ],
  templateUrl: './pdf-multi-panel.component.html',
  styleUrls: ['./pdf-multi-panel.component.scss']
})
export class PdfMultiPanelComponent {
  @Input() openDocuments: Documento[] = [];
  @Output() documentClosed = new EventEmitter<Documento>();
  @Output() documentOpened = new EventEmitter<{ documento: Documento; panelId: string }>();

  rootPanel = signal<PanelNode | null>(null);
  private nextId = 0;
  private dragState: {
    splitId: string;
    index: number;
    initialPos: number;
    initialSizes: number[]
  } | null = null;

  constructor(private snackBar: MatSnackBar) {
    // Crear un panel inicial vacío
    this.rootPanel.set({
      type: 'leaf',
      id: this.generateId(),
      documento: null
    });

    // Efecto para asignar documentos a paneles vacíos
    effect(() => {
      const docs = this.openDocuments;
      this.assignDocumentsToPanels(docs);
    });
  }

  // Nueva lógica: asignar documentos a paneles vacíos
  private assignDocumentsToPanels(documents: Documento[]): void {
    const root = this.rootPanel();
    if (!root) return;

    // Obtener todos los documentos cargados en paneles
    const loadedDocs = this.getAllLoadedDocuments(root);
    const loadedDocIds = new Set(loadedDocs.map(d => d.id));

    // Encontrar documentos nuevos (que no están cargados)
    const newDocs = documents.filter(d => !loadedDocIds.has(d.id));

    // Intentar asignar cada documento nuevo a un panel vacío
    for (const doc of newDocs) {
      const emptyPanel = this.findFirstEmptyPanel(root);
      if (emptyPanel) {
        this.assignDocumentToPanel(emptyPanel.id, doc);
      } else {
        // No hay paneles vacíos disponibles
        this.snackBar.open(
          'No hay paneles vacíos disponibles. Crea un nuevo panel o cierra un documento.',
          'Cerrar',
          { duration: 4000 }
        );
        // Remover el documento de la lista ya que no se pudo asignar
        this.documentClosed.emit(doc);
      }
    }

    // Remover documentos que ya no están en openDocuments
    const docsToRemove = loadedDocs.filter(d => !documents.some(doc => doc.id === d.id));
    for (const doc of docsToRemove) {
      this.unassignDocumentFromPanels(doc);
    }
  }

  private getAllLoadedDocuments(node: PanelNode): Documento[] {
    if (node.type === 'leaf') {
      return node.documento ? [node.documento] : [];
    }
    return node.children.flatMap(child => this.getAllLoadedDocuments(child));
  }

  private findFirstEmptyPanel(node: PanelNode): PanelLeaf | null {
    if (node.type === 'leaf' && node.documento === null) {
      return node;
    }
    if (node.type === 'split') {
      for (const child of node.children) {
        const emptyPanel = this.findFirstEmptyPanel(child);
        if (emptyPanel) return emptyPanel;
      }
    }
    return null;
  }

  private assignDocumentToPanel(panelId: string, documento: Documento | null): void {
    const root = this.rootPanel();
    if (!root) return;

    const newRoot = this.updatePanelDocument(root, panelId, documento);
    this.rootPanel.set(newRoot);
  }

  private updatePanelDocument(node: PanelNode, panelId: string, documento: Documento | null): PanelNode {
    if (node.type === 'leaf' && node.id === panelId) {
      return { ...node, documento };
    }
    if (node.type === 'split') {
      return {
        ...node,
        children: node.children.map(child => this.updatePanelDocument(child, panelId, documento))
      };
    }
    return node;
  }

  private unassignDocumentFromPanels(documento: Documento): void {
    const root = this.rootPanel();
    if (!root) return;

    // Buscar el panel con este documento y vaciarlo
    const panel = this.findPanelByDocumento(root, documento);
    if (panel) {
      this.assignDocumentToPanel(panel.id, null);
    }
  }

  private findPanelByDocumento(node: PanelNode, documento: Documento): PanelLeaf | null {
    if (node.type === 'leaf' && node.documento?.id === documento.id) {
      return node;
    }
    if (node.type === 'split') {
      for (const child of node.children) {
        const panel = this.findPanelByDocumento(child, documento);
        if (panel) return panel;
      }
    }
    return null;
  }

  private generateId(): string {
    return `panel-${this.nextId++}`;
  }

  // Crear un nuevo panel vacío
  createEmptyPanel(direction: SplitDirection = 'vertical'): void {
    const root = this.rootPanel();
    if (!root) {
      // Si no hay raíz, crear un panel vacío
      this.rootPanel.set({
        type: 'leaf',
        id: this.generateId(),
        documento: null
      });
      return;
    }

    // Encontrar el primer panel (vacío o con documento) y dividirlo
    const firstLeaf = this.findAnyLeaf(root);
    if (firstLeaf) {
      this.splitPanel(firstLeaf.id, direction, null);
    }
  }

  private findAnyLeaf(node: PanelNode): PanelLeaf | null {
    if (node.type === 'leaf') {
      return node;
    }
    for (const child of node.children) {
      const leaf = this.findAnyLeaf(child);
      if (leaf) return leaf;
    }
    return null;
  }

  // Dividir un panel en dos (puede recibir documento null para panel vacío)
  splitPanel(panelId: string, direction: SplitDirection, newDocumento: Documento | null): void {
    const root = this.rootPanel();
    if (!root) return;

    const newRoot = this.splitPanelRecursive(root, panelId, direction, newDocumento);
    this.rootPanel.set(newRoot);
  }

  private splitPanelRecursive(
    node: PanelNode,
    targetId: string,
    direction: SplitDirection,
    newDocumento: Documento | null
  ): PanelNode {
    if (node.id === targetId && node.type === 'leaf') {
      // Este es el panel a dividir
      const newLeaf: PanelLeaf = {
        type: 'leaf',
        id: this.generateId(),
        documento: newDocumento // Puede ser null (panel vacío) o un documento
      };

      return {
        type: 'split',
        id: this.generateId(),
        direction,
        children: [node, newLeaf],
        sizes: [50, 50]
      };
    }

    if (node.type === 'split') {
      return {
        ...node,
        children: node.children.map(child =>
          this.splitPanelRecursive(child, targetId, direction, newDocumento)
        )
      };
    }

    return node;
  }

  // Cerrar un panel y su documento
  closePanel(panelId: string): void {
    const root = this.rootPanel();
    if (!root) return;

    // Buscar el panel a cerrar
    const panelToClose = this.findPanelById(root, panelId);

    // Si es el único panel, dejarlo vacío en lugar de eliminarlo
    if (root.id === panelId && root.type === 'leaf') {
      if (root.documento) {
        this.documentClosed.emit(root.documento);
      }
      // Dejar el panel vacío
      this.rootPanel.set({
        ...root,
        documento: null
      });
      return;
    }

    // Si tiene documento, emitir evento de cierre
    if (panelToClose?.type === 'leaf' && panelToClose.documento) {
      this.documentClosed.emit(panelToClose.documento);
    }

    // Remover el panel del árbol
    const newRoot = this.closePanelRecursive(root, panelId);
    this.rootPanel.set(newRoot);
  }

  private findPanelById(node: PanelNode, panelId: string): PanelNode | null {
    if (node.id === panelId) {
      return node;
    }
    if (node.type === 'split') {
      for (const child of node.children) {
        const found = this.findPanelById(child, panelId);
        if (found) return found;
      }
    }
    return null;
  }

  // Método público para cerrar documento sin eliminar el panel
  closeDocumentInPanel(panelId: string): void {
    const root = this.rootPanel();
    if (!root) return;

    const panel = this.findPanelById(root, panelId);
    if (panel?.type === 'leaf' && panel.documento) {
      this.documentClosed.emit(panel.documento);
      this.assignDocumentToPanel(panelId, null);
    }
  }

  private closePanelRecursive(node: PanelNode, targetId: string): PanelNode | null {
    if (node.type === 'split') {
      // Filtrar el hijo que coincide con targetId
      const newChildren = node.children
        .map(child => {
          if (child.id === targetId) {
            // Emitir evento de cierre si es un leaf con documento
            if (child.type === 'leaf' && child.documento) {
              this.documentClosed.emit(child.documento);
            }
            return null;
          }
          return this.closePanelRecursive(child, targetId);
        })
        .filter((child): child is PanelNode => child !== null);

      // Si solo queda un hijo, promoverlo
      if (newChildren.length === 1) {
        return newChildren[0];
      }

      // Si no quedan hijos, retornar null
      if (newChildren.length === 0) {
        return null;
      }

      // Redistribuir tamaños
      const newSize = 100 / newChildren.length;
      return {
        ...node,
        children: newChildren,
        sizes: newChildren.map(() => newSize)
      };
    }

    return node;
  }

  // Manejo del resize
  onResizeStart(event: MouseEvent, splitId: string, separatorIndex: number): void {
    event.preventDefault();

    const root = this.rootPanel();
    if (!root) return;

    const split = this.findSplitById(root, splitId);
    if (!split) return;

    this.dragState = {
      splitId,
      index: separatorIndex,
      initialPos: split.direction === 'horizontal' ? event.clientX : event.clientY,
      initialSizes: [...split.sizes]
    };

    document.addEventListener('mousemove', this.onResizeMove);
    document.addEventListener('mouseup', this.onResizeEnd);
  }

  private onResizeMove = (event: MouseEvent): void => {
    if (!this.dragState) return;

    const root = this.rootPanel();
    if (!root) return;

    const split = this.findSplitById(root, this.dragState.splitId);
    if (!split) return;

    const currentPos = split.direction === 'horizontal' ? event.clientX : event.clientY;
    const delta = currentPos - this.dragState.initialPos;

    // Calcular el contenedor para obtener el porcentaje
    const container = document.getElementById(this.dragState.splitId);
    if (!container) return;

    const containerSize = split.direction === 'horizontal'
      ? container.offsetWidth
      : container.offsetHeight;

    const deltaPercent = (delta / containerSize) * 100;

    // Actualizar tamaños
    const newSizes = [...this.dragState.initialSizes];
    const leftIdx = this.dragState.index;
    const rightIdx = this.dragState.index + 1;

    newSizes[leftIdx] = Math.max(10, Math.min(90, this.dragState.initialSizes[leftIdx] + deltaPercent));
    newSizes[rightIdx] = Math.max(10, Math.min(90, this.dragState.initialSizes[rightIdx] - deltaPercent));

    const newRoot = this.updateSplitSizes(root, this.dragState.splitId, newSizes);
    this.rootPanel.set(newRoot);
  };

  private onResizeEnd = (): void => {
    this.dragState = null;
    document.removeEventListener('mousemove', this.onResizeMove);
    document.removeEventListener('mouseup', this.onResizeEnd);
  };

  private findSplitById(node: PanelNode, splitId: string): PanelSplit | null {
    if (node.type === 'split' && node.id === splitId) {
      return node;
    }
    if (node.type === 'split') {
      for (const child of node.children) {
        const found = this.findSplitById(child, splitId);
        if (found) return found;
      }
    }
    return null;
  }

  private updateSplitSizes(node: PanelNode, splitId: string, newSizes: number[]): PanelNode {
    if (node.type === 'split' && node.id === splitId) {
      return { ...node, sizes: newSizes };
    }
    if (node.type === 'split') {
      return {
        ...node,
        children: node.children.map(child => this.updateSplitSizes(child, splitId, newSizes))
      };
    }
    return node;
  }

  // Helpers para el template
  isSplit(node: PanelNode): node is PanelSplit {
    return node.type === 'split';
  }

  isLeaf(node: PanelNode): node is PanelLeaf {
    return node.type === 'leaf';
  }

  trackByPanelId(index: number, node: PanelNode): string {
    return node.id;
  }

  // Manejar split desde pdf-viewer (duplicar documento)
  onSplitDocument(event: { panelId: string; direction: SplitDirection; documento: Documento }): void {
    this.splitPanel(event.panelId, event.direction, event.documento);
  }
}
