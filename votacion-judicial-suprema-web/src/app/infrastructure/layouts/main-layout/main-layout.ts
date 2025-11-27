import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { NavbarComponent } from '../../shared/components/navbar/navbar.component';
import { MenuComponent } from '../../shared/components/menu/menu.component';
import { FooterComponent } from '../../shared/components/footer/footer.component';
import { Subscription, take } from 'rxjs';
import { LocalStorageUsuarioService } from '../../services/local/local-storage-usuario.service';
import { AppVjState } from '../../global-store/vj.reducers';
import { Store } from '@ngrx/store';
import { MenuItem } from '../../../domain/dto/local/menu-item';
import {MatSidenavModule} from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { Instancia } from '../../../domain/dto/remote/LoginResponse.dto';

@Component({
  selector: 'app-main-layout',
  imports: [CommonModule, 
    RouterOutlet, 
    NavbarComponent, 
    //MenuComponent, 
    //FooterComponent, 
    MatSidenavModule, 
    MatIconModule],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.scss'
})
export class MainLayout implements OnInit, OnDestroy {
  items: MenuItem[] = [];
  itemsNavbar: MenuItem[] = [];
  sala: Instancia | null = null;
  seleccionarOpcion: Subscription = new Subscription();
  constructor(
    private route: Router,
    private activatedRoute: ActivatedRoute,
    private localStorageUsuarioService: LocalStorageUsuarioService,
    private store: Store<AppVjState>
  ) {
    
  }

  ngOnInit(): void {
    this.localStorageUsuarioService.getSala().pipe(take(1))
      .subscribe(sala => {
        if(sala) this.sala = {...sala};
    });
    this.items = [
      {
        label: 'Salir',
        icon: 'logout',
        routeLink: '/autenticacion/login',
        selected:false
      },
    ];

    this.itemsNavbar =[
      {
        label: 'VOTACIONES',
        routeLink: '/votaciones/votacion',
        selected:false
      },
      {
        label: 'BANDEJA DE TRABAJO',
        routeLink: '/votaciones/bandeja',
        selected:false
      },
      {
        label: 'REPORTES',
        routeLink: '/reportes',
        selected:false
      },
    ];
  }

  ngOnDestroy(): void {
    this.seleccionarOpcion.unsubscribe();
  }
}


