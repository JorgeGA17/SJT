import { Component, signal, OnInit } from '@angular/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIcon} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../../services/remote/auth.service';
import Swal from 'sweetalert2';

import { Store } from '@ngrx/store';
import * as actions from '../../../../global-store/vj.actions';
import { AppVjState } from '../../../../global-store/vj.reducers';

import { CommonModule } from '@angular/common';
import { LoginRequest } from '../../../../../domain/dto/remote/LoginRequest.dto';
import { AuthUseCasesService } from '../../../../../use-cases/auth-use-cases.service';
import { LoginUseCasesService } from '../../../../../use-cases/login-use-cases.service';
import { LocalStorageService } from '../../../../services/local/local-storage.service';
import { LoginResponse } from '../../../../../domain/dto/remote/LoginResponse.dto';
import { constantes } from '../../../../../domain/commons/constants';


@Component({
  selector: 'app-login',
  imports: [CommonModule ,MatFormFieldModule,MatIcon, MatInputModule, MatButtonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login implements OnInit{
  hide = signal(true);
  dataLogin: LoginRequest = {
    usuario:"",
    clave: ""
  }
  regexAlfaNum = /^[a-zA-Z0-9]*$/;
  regexNumero = /^[0-9]+$/;
  usuarioValido = true;
  errorUsuario= "";

  passwordValido = true;
  errorPassword ="";

  errorresponse= false
  errorMensaje = ""

  constructor(
    private route: Router, 
    private activatedRoute: ActivatedRoute, 
    private localStorageService: LocalStorageService,
    private authUseCasesService: AuthUseCasesService,
    private loginUseCasesService: LoginUseCasesService,
    private store: Store<AppVjState>){

  }

  ngOnInit(): void {
  }

  autenticate(): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.authUseCasesService.getTokenAuth().subscribe({
      complete: () => {
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
      },
      error: (err) => {
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('Atención!', 'Error en la conexión con el servicio, recargue la página.');
      },
    });
  }

  limpiarErrorUsuario(){
    this.usuarioValido = true;
    this.errorUsuario= "";
  }

  limpiarErrorPass(){
    this.passwordValido = true;
    this.errorPassword ="";
  }

  validarUsuario():boolean{
    this.limpiarErrorUsuario();
    if(this.dataLogin.usuario=== ""){
      this.usuarioValido = false;
      this.errorUsuario= "Ingrese el usuario";
      return false;
    }
    return true;
  }
  
  validaPassword(): boolean{
    this.limpiarErrorPass();
    if(!this.dataLogin.clave || this.dataLogin.clave=== ""){
      this.passwordValido = false;
      this.errorPassword= "Ingrese la contraseña";
      return false;
    }
    return true;
  }
  login():void{
    this.limpiarErrorUsuario();
    this.limpiarErrorPass();
    this.dataLogin.usuario = this.dataLogin.usuario.trim();
    this.dataLogin.clave = this.dataLogin.clave.trim();
    if(!this.validarUsuario()){
      return;
    }
    if(!this.validaPassword()){
      return;
    }
    this.dataLogin.aplicaCaptcha = 'N';
    const tokenAut = this.localStorageService.getToken();
    if (!tokenAut) {
      Swal.fire('Atención!', 'Autorización requerida, inténtelo nuevamente.');
      //this.captchaElem?.reset();
      //this.loginLoad = false;
      this.autenticate();
      return;
    }

    this.store.dispatch(actions.mostrarCargando({ estado: true}));
    this.loginUseCasesService.login({ ...this.dataLogin }).subscribe({
      next: (data: LoginResponse) => {
        if (data.codigo === constantes.RES_COD_EXITO) {
          //this.captchaElem?.reset();
          this.route.navigate(['/autenticacion/seleccion-sala']);
        } else {
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          //this.captchaElem?.reset();
          //this.loginLoad = false;
          this.errorresponse = true;
          this.errorMensaje = data.descripcion;
        }
      },
      complete: () => {
        //this.loginLoad = false;
        //this.store.dispatch(actions.mostrarCargando({ estado: false}));
      },
      error: (err) => {
        //console.log('error login', err);
        Swal.fire('Atención!', err);
        //---------
        //this.captchaElem?.reset();
        //this.loginLoad = false;
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        this.autenticate();
        this.errorresponse = true;
        this.errorMensaje = "Lo sentimos. No es posible ingresar a la plataforma en este momento.";
      },
    });
  }
  
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }
}

