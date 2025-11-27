import { Directive, AfterViewInit, OnDestroy, Input, NgZone } from '@angular/core';
import { fromEvent, Subject } from 'rxjs';
import { takeUntil, switchMap, map } from 'rxjs/operators';

@Directive({
  selector: '[appDraggableDialog]',
  standalone: true
})
export class DraggableDialogDirective implements AfterViewInit, OnDestroy {
  @Input() dragHandle: string = '.pdf-toolbar';
  @Input() dragTarget: string = '.cdk-overlay-pane';

  private target: HTMLElement | null = null;
  private handle: HTMLElement | null = null;
  private delta = { x: 0, y: 0 };
  private offset = { x: 0, y: 0 };
  private destroy$ = new Subject<void>();

  constructor(private zone: NgZone) {}

  ngAfterViewInit(): void {
    // Dar tiempo para que el dialog se renderice completamente
    setTimeout(() => {
      this.setupElements();
      if (this.handle && this.target) {
        this.setupEvents();
      }
    }, 100);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private setupElements(): void {
    // Buscar el handle (toolbar)
    this.handle = document.querySelector(this.dragHandle);

    // Buscar el target (overlay pane del dialog)
    this.target = document.querySelector(this.dragTarget);

    if (this.target) {
      // Configurar el target para que sea posicionable
      this.target.style.position = 'fixed';
    }
  }

  private setupEvents(): void {
    if (!this.handle || !this.target) return;

    this.zone.runOutsideAngular(() => {
      const mousedown$ = fromEvent<MouseEvent>(this.handle!, 'mousedown');
      const mousemove$ = fromEvent<MouseEvent>(document, 'mousemove');
      const mouseup$ = fromEvent<MouseEvent>(document, 'mouseup');

      const mousedrag$ = mousedown$.pipe(
        switchMap((startEvent: MouseEvent) => {
          // No hacer drag si se clickea un botón o mat-icon
          const target = startEvent.target as HTMLElement;
          if (target.closest('button') || target.closest('mat-icon')) {
            return [];
          }

          const startX = startEvent.clientX;
          const startY = startEvent.clientY;

          // Cambiar cursor
          document.body.style.cursor = 'move';
          document.body.style.userSelect = 'none';

          return mousemove$.pipe(
            map((moveEvent: MouseEvent) => {
              moveEvent.preventDefault();
              this.delta = {
                x: moveEvent.clientX - startX,
                y: moveEvent.clientY - startY
              };
            }),
            takeUntil(mouseup$)
          );
        }),
        takeUntil(this.destroy$)
      );

      mousedrag$.subscribe(() => {
        if (this.delta.x === 0 && this.delta.y === 0) {
          return;
        }
        this.translate();
      });

      mouseup$.pipe(takeUntil(this.destroy$)).subscribe(() => {
        this.offset.x += this.delta.x;
        this.offset.y += this.delta.y;
        this.delta = { x: 0, y: 0 };

        // Restaurar cursor
        document.body.style.cursor = 'default';
        document.body.style.userSelect = 'auto';
      });
    });
  }

  private translate(): void {
    if (!this.target) return;

    requestAnimationFrame(() => {
      const newX = this.offset.x + this.delta.x;
      const newY = this.offset.y + this.delta.y;

      // Limitar a los bordes de la ventana
      const maxX = window.innerWidth - 200;
      const maxY = window.innerHeight - 100;

      const boundedX = Math.max(0, Math.min(newX, maxX));
      const boundedY = Math.max(0, Math.min(newY, maxY));

      this.target!.style.transform = `translate(${boundedX}px, ${boundedY}px)`;
    });
  }
}
