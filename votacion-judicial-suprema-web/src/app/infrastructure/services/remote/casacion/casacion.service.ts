import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from "rxjs";
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { RequestGenerarTablillaModel, RequestRelacionadosModel, RequestGrabarVoto, RequestListarDocumentosDigModel, RequestVerDocumentosDigModel } from 'src/app/domain/models/Casacion.model';

@Injectable({
    providedIn: 'root'
})
export class CasacionService {
    
    constructor(private httpClient: HttpClient, private route: Router/* , private authService: AuthService */) { }

    listarSentidos(distrito: string, provincia: string, instancia: string) {
        return this.httpClient.get(`${environment.urlApi}obtenerSentidos?distrito=${distrito}&provincia=${provincia}&instancia=${instancia}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }

    listarFallos(especialidad : string, estado : string, instancia: string) {
        return this.httpClient.get(`${environment.urlApi}obtenerFallos?especialidad=${especialidad}&abrev=${estado}&instancia=${instancia}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }    

    listarCasaciones(fechaSelecccionada: string, distrito: string, provincia: string, instancia: string) {
        return this.httpClient.get(`${environment.urlApi}casaciones?fecha=${fechaSelecccionada}&distrito=${distrito}&provincia=${provincia}&instancia=${instancia}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }   

    listarCasacionesRelacionados(params : RequestRelacionadosModel){
        return this.httpClient.post(`${ environment.urlApi }casaciones/listarRelacionados`, params).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    } 

    listarDocumentosDigitales(params : RequestListarDocumentosDigModel){
        return this.httpClient.get(`${environment.urlApi}casaciones/listar-documentos-digitales?nUnico=${params.nUnico}&nIncidente=${params.nIncidente}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    } 

    verDocumentoDigital(params : RequestVerDocumentosDigModel){
        return this.httpClient.get(`${environment.urlApi}casaciones/visualizar-documento-digital?nUnico=${params.nUnico}&nIncidente=${params.nIncidente}&nDocumento=${params.nDocumento}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    } 

    cargarDatosTablilla(va : RequestGenerarTablillaModel){
        return this.httpClient.post(`${ environment.urlApi }casaciones/generarTablilla`, va).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }

    grabarVoto(voto: RequestGrabarVoto) {
        return this.httpClient.post(`${environment.urlApi}casaciones/registrarVoto`, voto).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }    

    autoguardarVoto(voto: RequestGrabarVoto) {
        return this.httpClient.post(`${environment.urlApi}casaciones/autoguardado`, voto).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }        

    handleError(err: HttpErrorResponse): Observable<never> {
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
                errorMessage = 'Error en el servicio de consulta de casaciones, intente ingresar al sistema nuevamente';
            } else {
                errorMessage = `Error: ${err.status}\n Mensaje: ${err.message}`;
            }
        }
        return throwError(() => {
            return errorMessage;
        });
    }
}