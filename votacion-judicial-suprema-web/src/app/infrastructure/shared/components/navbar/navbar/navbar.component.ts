import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MenuItem } from 'primeng/api';
import {  faUser , faUserTie, faUserShield ,faUserCog } from '@fortawesome/free-solid-svg-icons';
import { LoginService } from 'src/app/infrastructure/services/remote/autenticacion/login.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/infrastructure/services/remote/autenticacion/auth.service';
//import { LoginService } from '../../../admin/core/services/login.service';
//import { Usuario } from 'src/app/pages/admin/core/models/LoginResponse.model';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})

export class NavbarComponent implements OnInit {
  @Input() profile:boolean = false;
  @Output() sidebarEvent = new EventEmitter();
  @Output() drawableEvent = new EventEmitter();

  sidebarShow:boolean = false;
  drawableShow:boolean = false;

  itemsEnd: MenuItem[]=[];
  nomUsuario: string;
  correoUsuario:string;
  perfilUsuario:string;
  title:string ="";
  faUser = faUser;
  faUserTie = faUserTie;
  faUserShield = faUserShield;
  faUserCog = faUserCog;
  
  dataUsuario:any;
  dataPerfil:any;
  constructor(private loginService: LoginService, private router: Router, private authService: AuthService) { 
    this.dataUsuario = this.loginService.getUsuario();
    this.dataPerfil = { title: ''};
    //this.dataUsuario = this.loginService.getUsuario();
    //this.dataPerfil = null;
    this.nomUsuario = 'nomUsuario';
    this.correoUsuario = 'correoUsuario';
    this.perfilUsuario = 'perfilUsuario';
  }

  ngOnInit(): void {
    
    if(this.dataUsuario){
      this.profile = true;
    }
    this.itemsEnd = [
      {
        separator:true
      },
      /*{
          label:'Canbiar contraseña',
          icon:'pi pi-lock',
          styleClass:"menu-profile-item p-ripple",
          routerLink: ['/autenticacion/cambiar-contrasenia'],
          routerLinkActiveOptions: { exact: true },
      },
      {
        separator:true
      },*/
      {
          label:'Salir',
          icon:'pi pi-sign-out',
          styleClass:"menu-profile-item p-ripple",
          command: () => this.salir(),
          routerLinkActiveOptions: { exact: true },
          
      }            
    ];
  }
  toggleSidebar(event:any):void{
    //console.log(console.log(event));
    this.sidebarShow = !this.sidebarShow;
    this.sidebarEvent.emit(this.sidebarShow);
  }
  
  toggleDrawable(event:any):void{
    //console.log(console.log(event));
    //this.drawableShow = !this.drawableShow;
    this.drawableEvent.emit(true);
  }

  irMain():void{
    this.router.navigate(['/votacion/main']);
  }

  salir():void{
    this.authService.logoutSession();
    this.router.navigate(['/autenticacion/login']);
  }

}
