import { Directive, OnInit, ElementRef, Renderer2, OnDestroy } from '@angular/core';
import { MatDialogContainer } from '@angular/material/dialog';

@Directive({
  selector: '[appResizableDialog]',
  standalone: true
})
export class ResizableDialogDirective implements OnInit, OnDestroy {
  private resizeState: {
    isResizing: boolean;
    direction: 'right' | 'bottom' | 'corner' | null;
    startX: number;
    startY: number;
    startWidth: number;
    startHeight: number;
  } | null = null;

  private resizeHandles: HTMLElement[] = [];
  private mouseMoveFn?: () => void;
  private mouseUpFn?: () => void;

  constructor(
    private matDialogContainer: MatDialogContainer,
    private elementRef: ElementRef,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.createResizeHandles();
  }

  ngOnDestroy(): void {
    this.removeResizeHandles();
    this.removeListeners();
  }

  private createResizeHandles(): void {
    const dialogElement = this.matDialogContainer['_elementRef'].nativeElement;
    const dialogParent = dialogElement.parentElement;
    if (!dialogParent) return;

    // Handle derecho
    const rightHandle = this.renderer.createElement('div');
    this.renderer.addClass(rightHandle, 'resize-handle');
    this.renderer.addClass(rightHandle, 'resize-handle-right');
    this.renderer.listen(rightHandle, 'mousedown', (e: MouseEvent) =>
      this.onResizeStart(e, 'right')
    );
    this.renderer.appendChild(dialogParent, rightHandle);
    this.resizeHandles.push(rightHandle);

    // Handle inferior
    const bottomHandle = this.renderer.createElement('div');
    this.renderer.addClass(bottomHandle, 'resize-handle');
    this.renderer.addClass(bottomHandle, 'resize-handle-bottom');
    this.renderer.listen(bottomHandle, 'mousedown', (e: MouseEvent) =>
      this.onResizeStart(e, 'bottom')
    );
    this.renderer.appendChild(dialogParent, bottomHandle);
    this.resizeHandles.push(bottomHandle);

    // Handle esquina
    const cornerHandle = this.renderer.createElement('div');
    this.renderer.addClass(cornerHandle, 'resize-handle');
    this.renderer.addClass(cornerHandle, 'resize-handle-corner');
    this.renderer.listen(cornerHandle, 'mousedown', (e: MouseEvent) =>
      this.onResizeStart(e, 'corner')
    );
    this.renderer.appendChild(dialogParent, cornerHandle);
    this.resizeHandles.push(cornerHandle);
  }

  private removeResizeHandles(): void {
    this.resizeHandles.forEach(handle => {
      if (handle.parentElement) {
        this.renderer.removeChild(handle.parentElement, handle);
      }
    });
    this.resizeHandles = [];
  }

  private onResizeStart(event: MouseEvent, direction: 'right' | 'bottom' | 'corner'): void {
    event.preventDefault();
    event.stopPropagation();

    const dialogElement = this.matDialogContainer['_elementRef'].nativeElement;
    const dialogParent = dialogElement.parentElement;
    if (!dialogParent) return;

    const rect = dialogParent.getBoundingClientRect();

    this.resizeState = {
      isResizing: true,
      direction,
      startX: event.clientX,
      startY: event.clientY,
      startWidth: rect.width,
      startHeight: rect.height
    };

    this.mouseMoveFn = this.renderer.listen('document', 'mousemove', (e: MouseEvent) =>
      this.onResizeMove(e)
    );
    this.mouseUpFn = this.renderer.listen('document', 'mouseup', () =>
      this.onResizeEnd()
    );

    document.body.style.cursor = direction === 'right' ? 'ew-resize' :
                                  direction === 'bottom' ? 'ns-resize' : 'nwse-resize';
  }

  private onResizeMove(event: MouseEvent): void {
    if (!this.resizeState?.isResizing) return;

    const dialogElement = this.matDialogContainer['_elementRef'].nativeElement;
    const dialogParent = dialogElement.parentElement;
    if (!dialogParent) return;

    const deltaX = event.clientX - this.resizeState.startX;
    const deltaY = event.clientY - this.resizeState.startY;

    if (this.resizeState.direction === 'right' || this.resizeState.direction === 'corner') {
      const newWidth = Math.max(400, this.resizeState.startWidth + deltaX);
      dialogParent.style.width = `${newWidth}px`;
      dialogParent.style.maxWidth = `${newWidth}px`;
    }

    if (this.resizeState.direction === 'bottom' || this.resizeState.direction === 'corner') {
      const newHeight = Math.max(300, this.resizeState.startHeight + deltaY);
      dialogParent.style.height = `${newHeight}px`;
      dialogParent.style.maxHeight = `${newHeight}px`;
    }
  }

  private onResizeEnd(): void {
    if (this.resizeState) {
      this.resizeState.isResizing = false;
      this.resizeState = null;
    }
    this.removeListeners();
    document.body.style.cursor = '';
  }

  private removeListeners(): void {
    if (this.mouseMoveFn) {
      this.mouseMoveFn();
      this.mouseMoveFn = undefined;
    }
    if (this.mouseUpFn) {
      this.mouseUpFn();
      this.mouseUpFn = undefined;
    }
  }
}
