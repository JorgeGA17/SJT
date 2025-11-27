import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { environment } from "src/environments/environment";
import { catchError, map } from 'rxjs/operators';
import { Observable, throwError } from "rxjs";
import { LoginRequest, LoginResponse, Usuario } from "../../../../domain/models/Login.model"
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { constantes } from 'src/app/constants';

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    public readonly USUARIO = constantes.USUARIO;
    public readonly USUARIO_OPCIONES = constantes.USUARIO_OPCIONES;
    public readonly USUARIO_DISTRITO = constantes.USUARIO_DISTRITO;
    public readonly USUARIO_PROVINCIA = constantes.USUARIO_PROVINCIA;
    public readonly USUARIO_SALA = constantes.USUARIO_SALA;
    public readonly USUARIO_NOMBRE_SALA = constantes.USUARIO_NOMBRE_SALA;
    constructor(private httpClient: HttpClient, private route: Router, private authService: AuthService) { }

    login(user: LoginRequest) {
        let newDAte: Date = new Date();
        return this.httpClient.post(`${environment.urlApi}authenticate/login`, user).pipe(
            map((result: any) => {
                let loginResponse: LoginResponse = result;
                if (loginResponse.codigo === '0000') {
                    this.authService.setDatetimeNewToken(newDAte);
                    this.authService.setTokenLevel(constantes.TOK_LVL_LOGIN);
                }
                return result;
            }),
            catchError(this.handleError)
        );
    }

    handleError(err: HttpErrorResponse): Observable<never> {
        //console.log("Error services ", err);
        let errorMessage = '';
        if (err.error instanceof ErrorEvent) {
            errorMessage = err.error.message;
        } else {
            if (!err.status) {
                return throwError(() => {
                    return err;
                });
            }
            if (err.status === 401 || err.status === 403) {
                errorMessage = 'Error en servicio de autenticación, intente ingresar al sistema nuevamente';
            } else {
                errorMessage = `Error: ${err.status}\n Mensaje: ${err.message}`;
            }
        }
        //console.log(errorMessage);
        return throwError(() => {
            return errorMessage;
        });
        //return throwError(() => err);
    }

    setUsuario(usuario: Usuario) {
        //console.log('setUsuario : ', usuario);
        localStorage.setItem(this.USUARIO, JSON.stringify(usuario));
    }

    getUsuario() {
        let usuarioLocal: string | null = localStorage.getItem(this.USUARIO);
        if (usuarioLocal) {
            return JSON.parse(usuarioLocal);
        } else {
            return null;
        }
    }

    setDistrito(distrito: any) {// perfil sleccionar
        localStorage.setItem(this.USUARIO_DISTRITO, JSON.stringify(distrito))
    }

    getDistrito() {
        let usuarioDistrito: string | null = localStorage.getItem(this.USUARIO_DISTRITO);
        if (usuarioDistrito) {
            return JSON.parse(usuarioDistrito);
        } else {
            return null;
        }
    } 

    setProvincia(provincia: any) {// perfil sleccionar
        localStorage.setItem(this.USUARIO_PROVINCIA, JSON.stringify(provincia))
    }

    getProvincia() {
        let usuarioProvincia: string | null = localStorage.getItem(this.USUARIO_PROVINCIA);
        if (usuarioProvincia) {
            return JSON.parse(usuarioProvincia);
        } else {
            return null;
        }
    }     

    setSala(sala: any) {// perfil sleccionar
        localStorage.setItem(this.USUARIO_SALA, JSON.stringify(sala))
    }

    getSala() {
        let usuarioSala: string | null = localStorage.getItem(this.USUARIO_SALA);
        if (usuarioSala) {
            return JSON.parse(usuarioSala);
        } else {
            return null;
        }
    }    

    setNombreSala(nombreSala: any) {// perfil sleccionar
        localStorage.setItem(this.USUARIO_NOMBRE_SALA, JSON.stringify(nombreSala))
    }

    getNombreSala() {
        let usuarioNombreSala: string | null = localStorage.getItem(this.USUARIO_NOMBRE_SALA);
        if (usuarioNombreSala) {
            return JSON.parse(usuarioNombreSala);
        } else {
            return null;
        }
    }

    getOpciones() {
        let opcionesLocal: string | null = localStorage.getItem(this.USUARIO_OPCIONES);
        //console.log("opciones login services", opcionesLocal);
        if (opcionesLocal) {
            return JSON.parse(opcionesLocal);
        } else {
            return null;
        }
    }

    removeUsuario() {
        localStorage.removeItem(this.USUARIO);
        localStorage.removeItem(this.USUARIO_OPCIONES);
        localStorage.removeItem(this.USUARIO_DISTRITO);
        localStorage.removeItem(this.USUARIO_PROVINCIA);
        localStorage.removeItem(this.USUARIO_SALA);
    }

    removeOpcion() {
        localStorage.removeItem(this.USUARIO_OPCIONES);
    }

    removeDistrito() {
        localStorage.removeItem(this.USUARIO_DISTRITO);
    }

    removeProvincia() {
        localStorage.removeItem(this.USUARIO_PROVINCIA);
    }
    
    removeSala() {
        localStorage.removeItem(this.USUARIO_SALA);
    }    

    removeNombreSala() {
        localStorage.removeItem(this.USUARIO_NOMBRE_SALA);
    }

    clear() {
        localStorage.clear();
    }

}
