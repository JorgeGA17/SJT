import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Usuario } from '../../../../domain/dto/remote/LoginResponse.dto';
import { LocalStorageUsuarioService } from '../../../services/local/local-storage-usuario.service';
import { Router, RouterLink } from '@angular/router';

import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import { MenuItem } from '../../../../domain/dto/local/menu-item';
import { NavSubmenuComponent } from './nav-submenu/nav-submenu.component';
import { MatRippleModule } from '@angular/material/core';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, 
    RouterLink, 
    MatIconModule, 
    MatButtonModule, 
    MatMenuModule,
    MatRippleModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit {
  @Input() profile = false;
  @Input() listaMenu: MenuItem[] = [];
  @Input() listaMenuEnd: MenuItem[] = [];
  @Output() sidebarEvent = new EventEmitter();
  @Output() drawableEvent = new EventEmitter();
  @Input() btnLogin = false;
  sidebarShow =false;
  drawableShow: boolean = false;

  showMenuMobile = false;
  
  nomUsuario: string;
  correoUsuario: string;
  perfilUsuario: string;
  title: string = '';
  

  dataUsuario$: Observable<Usuario | null>;
  dataPerfil: any;
  constructor(private localStorageUsuarioService: LocalStorageUsuarioService,  private route: Router) {
    this.dataUsuario$ = this.localStorageUsuarioService.getUsuario();
    this.dataPerfil = this.localStorageUsuarioService.getPerfil();
    this.nomUsuario = '';
    this.correoUsuario = '';
    this.perfilUsuario = '';
  }

  goLogin(): void {
    this.route.navigate(['/autenticacion/login']);
  }

  ngOnInit(): void {
  }
  toggleSidebar(event: any): void {
    //console.log(console.log(event));
    this.sidebarShow = !this.sidebarShow;
    this.sidebarEvent.emit(this.sidebarShow);
  }

  toggleDrawable(event: any): void {
    //console.log(console.log(event));
    //this.drawableShow = !this.drawableShow;
    this.drawableEvent.emit(true);
  }
}
