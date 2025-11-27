import { Component, OnInit } from '@angular/core';
import {  faUser , faUserTie, faUserShield ,faUserCog, faL } from '@fortawesome/free-solid-svg-icons';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from '../../../../services/remote/autenticacion/login.service';
// import { LocalStorageService } from 'src/app/infrastructure/services/local/local-storage.service';
// import { LocalStorageUsuarioService } from 'src/app/infrastructure/services/local/local-storage-usuario.service';
//import { Usuario } from 'src/app/domain/dto/LoginResponse.dto';
import Swal from 'sweetalert2';

//import { OpcionesRequest } from 'src/app/domain/dto/OpcionesRequest.dto';
//import { MenuOpcion, MenuOpcionesSistema, OpcionesResponse, OpcionSistema } from 'src/app/domain/dto/OpcionesResponse.dto';

import { Store } from '@ngrx/store';
import * as actions from '../../../../global-store/vj.actions';
import { AppVJState } from '../../../../global-store/vj.reducers';
import { constantes } from 'src/app/constants';
import { AuthService } from 'src/app/infrastructure/services/remote/autenticacion/auth.service';

@Component({
  selector: 'app-seleccion-sala',
  templateUrl: './seleccion-sala.component.html',
  styleUrls: ['./seleccion-sala.component.scss']
})
export class SeleccionSalaComponent implements OnInit  {
  faUser = faUser;// usuario
  faUserTie = faUserTie; // usuario operador (salaes nuevos)
  faUserShield = faUserShield; //usuario seguridad (sala monitoreo)
  faUserCog = faUserCog; // usuario configuracion (sala administtrador)

  listaSalas: any[] = [];
  salaSeleccionada: any;
  
  /* listaSalas: any[] = [
    {id:"1",title:"Ciudadano",description:"una descripcion",select:false, icon:faUserCog},
    {id:"2",title:"Service Desk",description:"una descripcion segundo",select:false, icon:faUserTie},
    {id:"3",title:"Administrador",description:"una descripcion tercero",select:false, icon:faUserShield}
  ]; */

  dataUsuario: any;

  esServiceDesk: boolean = false;
  constructor(private route: Router, 
    private activatedRoute: ActivatedRoute,
    private loginService: LoginService,
    private authService:  AuthService,
    // private localStorageService: LocalStorageService,
    // private localStorageUsuarioService:LocalStorageUsuarioService,
    private store: Store<AppVJState>) {
    this.dataUsuario = this.loginService.getUsuario();
    //this.esServiceDesk = this.localStorageUsuarioService.esServiceDesk();
    //console.log(this.dataUsuario);

  }

  ngOnInit(): void {
    if(this.dataUsuario){
      //console.log(this.dataUsuario);
      this.dataUsuario.instancias.forEach(
        ( sala: any) =>{
        this.listaSalas.push(
          {
            id :sala.codigoInstancia,
            title: sala.nombreInstancia,
            select:false,
            distrito: sala.codigoDistrito,
            provincia: sala.codigoProvincia
          }
        )
      });

      if(this.listaSalas.length==1){
        this.salaSeleccionada = this.listaSalas[0];
        this.ingresar();
      }
    }
  }

  // getIcon(sala:string){
  //   switch (sala.toLowerCase()) {
  //     case 'administrador':
  //       return faUserCog;
  //     case 'monitoreador':
  //       return faUserShield;
  //     default:
  //       return faUserTie;
  //   }
  // }
  
  ingresar():void{
    if(this.salaSeleccionada){

      this.loginService.removeDistrito();
      this.loginService.removeProvincia();
      this.loginService.removeSala();
      this.loginService.removeNombreSala();

      this.loginService.setSala(this.salaSeleccionada.id);
      this.loginService.setNombreSala(this.salaSeleccionada.title);
      this.loginService.setDistrito(this.salaSeleccionada.distrito);
      this.loginService.setProvincia (this.salaSeleccionada.provincia);
      // this.authService.setToken(data.data.token);
      this.route.navigate(['/votacion/main']);
    } else{
      Swal.fire('Atención!', "Seleccione un sala antes de ingresar al sistema", 'warning');
    }
  }

  // buscarHijos(responseOpciones:MenuOpcion[],id:number, uri:String):OpcionSistema[]{
  //     let menuHijos:OpcionSistema[] = [];
  //     let menuHijosHijos:OpcionSistema[] = [];

  //     let menu:OpcionSistema;
  //     for(let i = 0; i< responseOpciones.length; i++){
  //       if(responseOpciones[i].idOpcionSuperior == id){
  //         menuHijosHijos = this.buscarHijos(responseOpciones,responseOpciones[i].id,uri + responseOpciones[i].url );
  //         menu = {
  //           id: responseOpciones[i].id ,
  //           title: responseOpciones[i].nombre, 
  //           description: "",
  //           select: false,
  //           activate: false,
  //           icon: responseOpciones[i].icono,
  //           url: uri+responseOpciones[i].url,
  //           hijos: menuHijosHijos};
  //         menuHijos.push(menu);
  //       }
  //     }
  //     return menuHijos;
  // }

  // gererarMenuOpciones(responseOpciones:MenuOpcion[]):void{
  //   let menuPadres:OpcionSistema[] = [];
  //   let menuHijos:OpcionSistema[] = [];
  //   let menu:OpcionSistema;
  //   for(let i = 0; i< responseOpciones.length; i++){
  //     if(responseOpciones[i].idOpcionSuperior == null){
  //       menuHijos = this.buscarHijos(responseOpciones,responseOpciones[i].id,responseOpciones[i].url);
  //       menu = {
  //         id: responseOpciones[i].id,
  //         title: responseOpciones[i].nombre,
  //         description: "",
  //         select: false,
  //         activate: false,
  //         icon: responseOpciones[i].icono,
  //         url: responseOpciones[i].url,
  //         hijos: menuHijos
  //       };
  //       menuPadres.push(menu);
  //     }
  //   }
  //   //console.log("menu generado ",menuPadres);
  //   this.localStorageUsuarioService.setOpciones({opciones: menuPadres});
  //   let urlsPermitods = this.generarUrlsPermitidos(menuPadres);
  //   this.localStorageUsuarioService.setUrlsPermitidos(urlsPermitods);
  //   this.irPrimerUrl(menuPadres);
  // }

  // generarUrlsPermitidos(listaOps:OpcionSistema[]):string[]{
  //   let urlsP:string[] = [];
  //   for(let i =0;i<listaOps.length;i++){
  //     if(listaOps[i].hijos.length>0){
  //       urlsP = urlsP.concat(this.generarUrlsPermitidos(listaOps[i].hijos));
  //     }
  //     else{
  //       urlsP.push(listaOps[i].url);
  //     }
  //   }
  //   return urlsP;
  // }
  // irPrimerUrl(listaOps:OpcionSistema[]){
  //   for(let i =0;i<listaOps.length;i++){
  //     if(listaOps[i].hijos.length>0){
  //       this.irPrimerUrl(listaOps[i].hijos);
  //       break;
  //     }
  //     else{
  //       this.route.navigate([listaOps[i].url]);
  //       break;
  //     }
  //   }
  // }
  selectedSala(indice:number, sala:any):void{
    this.listaSalas.forEach(function(element, index, array){
      element.select = false;
    });
    this.listaSalas[indice].select=true;
    this.salaSeleccionada = sala;
    //localStorage.setItem('sala', this.listaSalas[indice].id);
  }

}
