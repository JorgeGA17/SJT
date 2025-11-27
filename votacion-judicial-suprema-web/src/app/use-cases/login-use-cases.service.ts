import { Injectable } from '@angular/core';
import { LoginService } from '../infrastructure/services/remote/login.service';
import { LoginRequest } from '../domain/dto/remote/LoginRequest.dto';
import { catchError, forkJoin, map, Observable, of, switchMap, tap, throwError } from 'rxjs';
import { LoginResponse } from '../domain/dto/remote/LoginResponse.dto';
import { constantes, mensajes, tokenNiveles } from '../domain/commons/constants';
import { LocalStorageService } from '../infrastructure/services/local/local-storage.service';
import { LocalStorageUsuarioService } from '../infrastructure/services/local/local-storage-usuario.service';
import { ErrorValidacion } from '../domain/dto/local/ErrorValidacion.dto';
import { OpcionesRequest } from '../domain/dto/remote/OpcionesRequest.dto';
import { OpcionesResponse } from '../domain/dto/remote/OpcionesResponse.dto';

@Injectable({
  providedIn: 'root',
})
export class LoginUseCasesService {
  constructor(
    private loginService: LoginService,
    private localStorageService: LocalStorageService,
    private localStorageUsuarioService: LocalStorageUsuarioService
  ) {}

  validarUsuario(usuario:string): ErrorValidacion{
    let err: ErrorValidacion = {
      campo:"Usuario",
      mensaje:"",
      valido: true
    }
    if(usuario=== ""){
      err.mensaje = "Ingrese el usuario"
      err.valido = false;
      return err;
    }
    return err;
   
  }
  validaPassword(password:string): ErrorValidacion{
    let err: ErrorValidacion = {
      campo:"Contraseña",
      mensaje:"",
      valido: true
    }
    if(!password || password=== ""){
      err.mensaje = "Ingrese la contraseña"
      err.valido = false;
      return err;
    }
    if(password.length <=6 ){
      err.mensaje = "La contraseña solo debe tener mas de 6 caractéres"
      err.valido = false;
      return err;
    }
    return err;
  }

  validarCampos(user: LoginRequest): ErrorValidacion[] {
    const errors: ErrorValidacion[] = [];

    if (!user.usuario! || user.usuario! === '') {
      errors.push({ campo: 'Usuario', mensaje: 'El usuario es requerido.' });
    }
    if (!user.clave! || user.clave! === '') {
      errors.push({ campo: 'Contraseña', mensaje: 'La constraseña es requerido.' });
    }

    return errors;
  }

  login(user: LoginRequest): Observable<LoginResponse> {
    const erroresValidacion = this.validarCampos(user);
    let newDAte: Date = new Date();
    if (erroresValidacion.length > 0) {
      let errorMensaje = '';
      erroresValidacion.forEach((error) => {
        errorMensaje += `Error en el campo <b>"${error.campo}"</b>: ${error.mensaje} <br/>`;
      });
      return throwError(() => errorMensaje);
    }

    return this.loginService.login(user).pipe(
      /*map((response: LoginResponse) => {
        if (response.codigo === constantes.RES_COD_EXITO) {
          this.localStorageService.setDatetimeNewToken(newDAte);
          this.localStorageUsuarioService.setUsuario(response.data);
          this.localStorageService.setToken(response.data.token);
          this.localStorageService.setTokenNivel(tokenNiveles.NIVEL_LOGIN);
          if (!response.data) {
            response.codigo = constantes.RES_COD_NO_DATA;
            response.descripcion = mensajes.MSG_RESP_NO_DATA;
          }
        }
        return response;
      })*/
      switchMap((response: LoginResponse) => {
        if (response.codigo !== constantes.RES_COD_EXITO) {
          return of(response);
        }
        return forkJoin({
          tokenNivel: this.localStorageService.setTokenNivel(tokenNiveles.NIVEL_LOGIN).pipe(
            catchError(err => {
              response.codigo = constantes.RES_COD_NO_DATA;
              response.descripcion = "Error al guardar token nivel";
              return of(response);
            })
          ),
          usuario:this.localStorageUsuarioService.setUsuario(response.data).pipe(
             catchError(err => {
              response.codigo = constantes.RES_COD_NO_DATA;
              response.descripcion = "Error al guardar usuario";
              return of(response);
            })
          ),

        }).pipe(
          tap(() => {
            this.localStorageService.setDatetimeNewToken(newDAte);
            this.localStorageService.setToken(response.data.token);

            if (!response.data) {
              response.codigo = constantes.RES_COD_NO_DATA;
              response.descripcion = mensajes.MSG_RESP_NO_DATA;
            }
          }),
          map(() => response)
        );
      }),
      catchError(error => {
        console.error('Error al guardar token nivel:', error);
        return throwError(() => error);
      })
    );
  }

  obtenerOpciones(req: OpcionesRequest): Observable<OpcionesResponse> {
    const newDate: Date = new Date();
    return this.loginService.opciones(req).pipe(
      switchMap((response: OpcionesResponse) => {
        if (response.codigo !== constantes.RES_COD_EXITO) {
          return of(response);
        }

        return forkJoin({
          tokenNivel: this.localStorageService.setTokenNivel(tokenNiveles.NIVEL_OPCIONES).pipe(
            catchError(err => {
              response.codigo = constantes.RES_COD_NO_DATA;
              response.descripcion = "Error al guardar token nivel";
              return of(response);
            })
          ),
        }).pipe(
          tap(() => {
            this.localStorageService.setToken(response.data.token);
            this.localStorageService.setDatetimeNewToken(newDate);
            
            if (!response.data) {
              response.codigo = constantes.RES_COD_NO_DATA;
              response.descripcion = mensajes.MSG_RESP_NO_DATA;
            }
          }),
          map(() => response)
        );
      }),
      catchError(error => {
          console.error('Error al guardar token nivel:', error);
          return throwError(() => error);
      })
    );
  }
}
