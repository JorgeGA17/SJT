import { Component, signal, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Store } from '@ngrx/store';
import { AppVjState} from './infrastructure/global-store/vj.reducers';

import {
  Router,
  Event as RouterEvent,
  NavigationStart,
  NavigationEnd,
  NavigationCancel,
  NavigationError
} from '@angular/router'

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  protected readonly title = signal('votacion-judicial-web');

  constructor(private router: Router, private store: Store<AppVjState>){
    router.events.subscribe((event: RouterEvent) => {
        this.navigationInterceptor(event)
      });
  }

  public showOverlay = true;
  ngOnInit(): void {
    this.store.select('mostrarCargando').subscribe(({estado}) => {
      if(estado){
        this.mostrarCargando();
        //console.log("mostrar cargando True");
      } 
      else{
        this.ocultarCargando();
      }
    });
  }
  mostrarCargando() {    
    document.getElementById("cargando")?.classList.add('show-loading');
  }

  ocultarCargando() {
    document.getElementById("cargando")?.classList.remove('show-loading');
  }
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
}
