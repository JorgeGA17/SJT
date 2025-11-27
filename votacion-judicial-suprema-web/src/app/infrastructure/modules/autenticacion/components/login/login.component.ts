import { Component, ViewChild, OnInit, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import Swal from 'sweetalert2';

import { AuthService } from '../../../../services/remote/autenticacion/auth.service';
import { LoginService } from '../../../../services/remote/autenticacion/login.service';
import { RecaptchaComponent, RecaptchaErrorParameters } from 'ng-recaptcha';

import { AppVJState } from '../../../../global-store/vj.reducers';
import * as actions from '../../../../global-store/vj.actions';
import { LoginRequest , LoginResponse } from '../../../../../domain/models/Login.model';
import { environment } from 'src/environments/environment';
import { constantes } from 'src/app/constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, AfterViewInit {
  tokenLoad: boolean = false;
  loginLoad: boolean = false;
  loginBlock: boolean = false;
  user: LoginRequest;

  currentDate :Date = new Date();
  tipodocSeleccionado: { codigo: '', descripcion: '' } | undefined;

    //------------- captcha config ---
    flagCaptcha: string = environment.flagCaptcha;
    capchaKey: string = environment.tokenCaptcha;
    recaptcha:any = (window as any).grecaptcha;
    capchaLoad: boolean=false;
    tokenCapcha:string | null=null;
    tokenCapchaLast:string | null=null;
    errorCapcha:boolean=false;
    //--
    @ViewChild('captchaElem', { static: false }) captchaElem: RecaptchaComponent | null = null;


    onSubmit(form: any) {
      if (form.valid) {
        //console.log('Form Submitted', form.value);
      } else {
        //console.log('Form is invalid');
      }
    }

  constructor(private store: Store<AppVJState>,
    private router: Router,
    private authService: AuthService,
    private loginService: LoginService) {

    this.authService.logoutSession();
    //this.autenticate();

    this.user = {
      usuario: '',
      clave: '',
      tokenCaptcha: '',
      aplicaCaptcha: this.flagCaptcha
    };
  }

  autenticate(): void {
    this.tokenLoad = false;
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.authService.recuperarTokenAutorization().subscribe({
      next: (data: any) => {
        this.authService.setToken(data.token);
        this.authService.setTimeTokenValido(data.exps);
        this.authService.setTimeRefreshValido(data.refs);
      },
      complete: () => {
        this.tokenLoad = true;
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
      },
      error: (err: any) => {
        this.tokenLoad = true;
        this.loginBlock = true
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', "Error en la conexión con el servicio, recargue la página.", 'warning');
      }
    });
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.autenticate();
  }

  loginParte2(): void {
    this.user.tokenCaptcha = this.tokenCapcha?this.tokenCapcha:'';
    this.loginService.login({ ...this.user }).subscribe({
      next: (data: LoginResponse) => {
        if (data.codigo === constantes.RESPONSE_COD_EXITO) {
          this.loginService.setUsuario(data.data.usuario);
          this.authService.setToken(data.data.usuario.token);
          //this.router.navigate(['/votacion/main']);
          this.router.navigate(['/autenticacion/seleccion-sala']);
        }
        else {
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          this.tokenLoad = true;
          Swal.fire('¡Atención!', data.descripcion, 'info').then((result) => {
            if (result.isConfirmed || result.isDismissed) {
              this.tokenLoad = true;
              this.captchaElem?.reset();
            }
          });
        }
      },
      complete: () => {
        this.tokenLoad = true;
      },
      error: (err) => {
        Swal.fire('¡Atención!', err, 'warning').then((result) => {
          if (result.isConfirmed || result.isDismissed) {
            this.tokenLoad = true;
            this.captchaElem?.reset();
          }
        });
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        this.autenticate();
      }
    });
    this.tokenLoad = true;
  }

  login(): void {
    this.tokenLoad = false;
    if (this.user.usuario) {
      this.user.usuario = this.user.usuario.trim();
    }
    if (this.user.clave) {
      this.user.clave = this.user.clave.trim();
    }
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    
    if (this.user.usuario && this.user.usuario != "" && this.user.clave && this.user.clave != "") {
      this.user.aplicaCaptcha = this.flagCaptcha;
      if(this.flagCaptcha=='S'){
        this.captchaElem?.execute();
      } else {
        this.loginParte2();
      }
    } else {
      this.store.dispatch(actions.mostrarCargando({ estado: false }));
      this.tokenLoad = true;
      Swal.fire('¡Atención!', 'Complete usuario y contraseña, para ingresar al sistema.', 'warning');
    }
    this.tokenLoad = true;
  }

  reloadLogin() {
    location.reload();
  }

  public resolved(captchaResponse: string): void {
    this.tokenCapcha = captchaResponse;
    this.tokenCapchaLast = captchaResponse;
    if (this.tokenCapcha){
      this.loginParte2();
    }
  }

  public onError(errorDetails: RecaptchaErrorParameters): void {  
    Swal.fire('¡Atención!', 'Error en configuración de CAPTCHA.', 'warning');
    this.tokenLoad = true;
  }

  public toUpperCase(user : string): void {
    this.user.usuario =  user.toUpperCase();
  }

}
