import { Component, OnDestroy, OnInit } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { Store } from '@ngrx/store';
import { AppVJState } from "./infrastructure/global-store/vj.reducers"; 
//import { AppVJState } from './infrastructure/global-store/sv.reducers';
import {
  Router,
  Event as RouterEvent,
  NavigationStart,
  NavigationEnd,
  NavigationCancel,
  NavigationError
} from '@angular/router';
import { Observable } from 'rxjs';
import { mensajes } from './constants';
import Swal from 'sweetalert2';
import { LocalStatesService } from './infrastructure/services/local/local-states.service';
import { AuthService } from './infrastructure/services/remote/autenticacion/auth.service';
import { LoginService } from './infrastructure/services/remote/autenticacion/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  public showOverlay = true;
  title = 'votacion-judicial-suprema-web';
  eventStateTokenExpired$:Observable<any> = new Observable();
  intervalTimeOut: any;

  constructor(private primengConfig: PrimeNGConfig,
    private translate: TranslateService,
    private store: Store<AppVJState>,
    private router: Router,
    private localStateService: LocalStatesService,
    private loginService: LoginService,
    private authService: AuthService) { 
      router.events.subscribe((event: RouterEvent) => {
        this.navigationInterceptor(event)
      });
  }
  ngOnInit() {
    this.translate.setDefaultLang("es");
    this.translate.use("es");
    this.translate.get('primeng').subscribe(res => this.primengConfig.setTranslation(res));
    this.primengConfig.ripple = true;
    this.store.select('mostrarCargando').subscribe(({estado}) => {
      if(estado){
        this.mostrarCargando();
      } 
      else{
        this.ocultarCargando();
      }
    });

    this.eventStateTokenExpired$.subscribe((tokenExpired:any) => {
      if(tokenExpired){ 
        setTimeout(() => {
          this.cerrarventana(mensajes.SWAL_TITLE_TOKEN_EXPIRA,'El aplicativo se cerrará en este momento');
        }, 1000);
      }
    });

    this.intervalTimeOut = setInterval(() => {
      if(this.loginService.getUsuario()){
        const timeSession = this.authService.getDiffTimeSession();
        const timeExp = this.authService.getTimeTokenValido();
        const timeRef = this.authService.getTimeRefreshValido();
        //console.log(timeSession);
        if(timeSession > (timeExp + timeRef)){
          if(Swal.isVisible()){
            const SWALTitle = Swal.getTitle()?.textContent;
            if(SWALTitle !== mensajes.SWAL_TITLE_TOKEN_EXPIRA){
              this.cerrarventana(mensajes.SWAL_TITLE_TOKEN_EXPIRA,'El aplicativo se cerrará en este momento');
            }
          }
          else{
            this.cerrarventana(mensajes.SWAL_TITLE_TOKEN_EXPIRA,'El aplicativo se cerrará en este momento');
          }

          return;
        }

        if(timeSession > timeExp){
          const segResta = timeExp + timeRef - timeSession;
          if(!Swal.isVisible()){
            Swal.fire({
              title:"Atención",
              html: `Tu sesión esta por finalizar, quedan ${this.convertirSegundos(segResta)}`,
              showDenyButton: true,
              allowOutsideClick: false,
              confirmButtonText: 'Continuar en la plataforma',
              denyButtonText: `Salir`,
            }).then((result) => {
              if (result.isConfirmed) {
                this.authService.setRefreshToken();
              } 
              else if (result.isDenied) {
                this.router.navigate(['/autenticacion/login']);
              }
            })
            return;

          }
          else{
            Swal.update({
              html: `Tu sesión esta por finalizar, quedan ${this.convertirSegundos(segResta)}`
            });
            
            return;
          }
        }
        
      }
      
    }, 1000);
  }  

  convertirSegundos(segundos: number): string {
    const horas = Math.floor(segundos / 3600);              // horas completas
    const minutos = Math.floor((segundos % 3600) / 60);     // minutos restantes
    const restoSegundos = segundos % 60;                    // segundos restantes
    if (horas > 0){
       return `${horas} h ${minutos} min ${restoSegundos} s`;
    }
    if (minutos > 0){
       return `${minutos} min ${restoSegundos} s`;
    }
    return `${restoSegundos} segundos`;
  }

  cerrarventana(mensaje:string, bodyHtml:string){
    Swal.fire({
      title: mensaje,
      html:bodyHtml,
      confirmButtonText: "OK",
      allowOutsideClick:false
    }).then((result) => {
      if (result.isConfirmed) {
        this.router.navigate(['/autenticacion/login']);
      } 
    });
  }

  mostrarCargando() {    
    document.getElementById("cargando")?.classList.add('show-loading');
  }

  ocultarCargando() {
    document.getElementById("cargando")?.classList.remove('show-loading');
  }

    // navigator interceptor
    navigationInterceptor(event: RouterEvent): void {
      if (event instanceof NavigationStart) {
        this.showOverlay = true;
        this.mostrarCargando();
      }
      if (event instanceof NavigationEnd) {
        this.showOverlay = false;
        this.ocultarCargando();
      }

      // Set loading state to false in both of the below events to hide the spinner in case a request fails
      if (event instanceof NavigationCancel) {
        this.showOverlay = false;
        this.ocultarCargando();
      }
      if (event instanceof NavigationError) {
        this.showOverlay = false;
        this.ocultarCargando();
      }
    }
  
  ngOnDestroy(): void {
    if (this.intervalTimeOut) {
      clearInterval(this.intervalTimeOut);
    }
  }
}
