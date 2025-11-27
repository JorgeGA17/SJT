import { Injectable, signal, computed } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { toSignal } from '@angular/core/rxjs-interop';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {
  // Señales para control de layout
  private sidenavOpenSignal = signal<boolean>(true);
  private pdfSidebarOpenSignal = signal<boolean>(true);

  readonly sidenavOpen = this.sidenavOpenSignal.asReadonly();
  readonly pdfSidebarOpen = this.pdfSidebarOpenSignal.asReadonly();

  // Observar breakpoints
  private breakpointState!: ReturnType<typeof toSignal<any>>;

  // Computed signals para diferentes tamaños de pantalla
  readonly isXSmall = computed(() =>
    this.breakpointState()?.breakpoints[Breakpoints.XSmall] || false
  );

  readonly isSmall = computed(() =>
    this.breakpointState()?.breakpoints[Breakpoints.Small] || false
  );

  readonly isMedium = computed(() =>
    this.breakpointState()?.breakpoints[Breakpoints.Medium] || false
  );

  readonly isLarge = computed(() =>
    this.breakpointState()?.breakpoints[Breakpoints.Large] || false
  );

  readonly isXLarge = computed(() =>
    this.breakpointState()?.breakpoints[Breakpoints.XLarge] || false
  );

  // Computed para determinar el tipo de dispositivo
  readonly isMobile = computed(() => this.isXSmall());
  readonly isTablet = computed(() => this.isSmall() || this.isMedium());
  readonly isDesktop = computed(() => this.isLarge() || this.isXLarge());

  // Computed para el modo del sidenav
  readonly sidenavMode = computed<'side' | 'over'>(() =>
    this.isDesktop() ? 'side' : 'over'
  );

  // Computed para determinar si el sidenav debe estar abierto por defecto
  readonly sidenavDefaultOpen = computed(() => this.isDesktop());

  constructor(private breakpointObserver: BreakpointObserver) {
    // Inicializar breakpointState
    this.breakpointState = toSignal(
      this.breakpointObserver.observe([
        Breakpoints.XSmall,
        Breakpoints.Small,
        Breakpoints.Medium,
        Breakpoints.Large,
        Breakpoints.XLarge
      ])
    );

    // Ajustar el sidenav automáticamente según el tamaño de pantalla
    this.setupResponsiveBehavior();
  }

  // Toggle sidenav
  toggleSidenav(): void {
    this.sidenavOpenSignal.update(open => !open);
  }

  // Abrir sidenav
  openSidenav(): void {
    this.sidenavOpenSignal.set(true);
  }

  // Cerrar sidenav
  closeSidenav(): void {
    this.sidenavOpenSignal.set(false);
  }

  // Toggle PDF sidebar
  togglePdfSidebar(): void {
    this.pdfSidebarOpenSignal.update(open => !open);
  }

  // Abrir PDF sidebar
  openPdfSidebar(): void {
    this.pdfSidebarOpenSignal.set(true);
  }

  // Cerrar PDF sidebar
  closePdfSidebar(): void {
    this.pdfSidebarOpenSignal.set(false);
  }

  // Configurar comportamiento responsive
  private setupResponsiveBehavior(): void {
    // Observar cambios en el breakpoint
    this.breakpointObserver
      .observe([Breakpoints.Large, Breakpoints.XLarge])
      .subscribe(result => {
        // En desktop, mantener sidenav abierto por defecto
        if (result.matches && !this.sidenavOpenSignal()) {
          this.sidenavOpenSignal.set(true);
        }
      });
  }

  // Obtener ancho óptimo para el sidenav según el dispositivo
  getSidenavWidth(): string {
    if (this.isMobile()) {
      return '100%';
    } else if (this.isTablet()) {
      return '300px';
    } else {
      return '350px';
    }
  }

  // Obtener clase CSS según el tamaño de pantalla
  getDeviceClass(): string {
    if (this.isMobile()) {
      return 'mobile-layout';
    } else if (this.isTablet()) {
      return 'tablet-layout';
    } else {
      return 'desktop-layout';
    }
  }
}
