import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from "rxjs";
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { RequestValidarDocumento } from 'src/app/domain/models/Casacion.model';

@Injectable({
    providedIn: 'root'
})
export class ProyectosService {
    
    constructor(private httpClient: HttpClient, private route: Router) { }

    obtenerEstadosProyectos(){
        return this.httpClient.get(`${environment.urlApi}obtenerEstadosProyecto`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }

    obtenerEstadosVot(){
        return this.httpClient.get(`${environment.urlApi}obtenerEstadosVotacion`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }
    
    obtenerResponsablesVot(){
        return this.httpClient.get(`${environment.urlApi}obtenerResponsables`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }    

    descargarReporte(distrito: string, provincia: string, instancia: string, usuario: string, estadoProy: string|null, estadoVot: string|null, fInicio: string|null, ffin: string|null, tipoReporte : string){
        return this.httpClient.get(`${environment.urlApi}proyectos/descargarReporte?distrito=${distrito}&provincia=${provincia}&instancia=${instancia}&usuarioResponsable=${usuario}&estadoProyecto=${estadoProy}&estadoVotacion=${estadoVot}&fechaInicio=${fInicio}&fechaFin=${ffin}&tipoReporte=${tipoReporte}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }

    visualizarReporte(distrito: string, provincia: string, instancia: string, usuario: string, estadoProy: string|null, estadoVot: string|null, fInicio: string|null, ffin: string|null, tipoReporte : string){
        return this.httpClient.get(`${environment.urlApi}proyectos/generarReporte?distrito=${distrito}&provincia=${provincia}&instancia=${instancia}&usuarioResponsable=${usuario}&estadoProyecto=${estadoProy}&estadoVotacion=${estadoVot}&fechaInicio=${fInicio}&fechaFin=${ffin}&tipoReporte=${tipoReporte}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }    

    listarProyectos(distrito: string, provincia: string, instancia: string, usuario: string, estado: string|null, fInicio: string|null, ffin: string|null) {
        return this.httpClient.get(`${environment.urlApi}proyectos/listar?distrito=${distrito}&provincia=${provincia}&instancia=${instancia}&usuarioResponsable=${usuario}&idEstado=${estado}&fechaInicio=${fInicio}&fechaFin=${ffin}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }

    listarProyectosPorValidar(distrito: string, provincia: string, instancia: string, usuario: string, estado: string|null, fInicio: string|null, ffin: string|null){
        return this.httpClient.get(`${environment.urlApi}proyectos/validados?distrito=${distrito}&provincia=${provincia}&instancia=${instancia}&usuarioResponsable=${usuario}&idEstado=${estado}&fechaInicio=${fInicio}&fechaFin=${ffin}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );        
    }
    
    listarRelacionados(nunico: string, incidente: string, nsentido: string, nvotacion: string, usuario: string) {
        return this.httpClient.get(`${environment.urlApi}proyectos/relacionados?numeroUnico=${nunico}&numeroIncidente=${incidente}&numeroSentido=${nsentido}&numeroVotacion=${nvotacion}&usuarioResponsable=${usuario}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }

    // enviarDocumento(rqEnviarDocumento: RequestEnviarDocumento) {
    //     return this.httpClient.post(`${environment.urlApi}proyectos/registrarEnvio`, rqEnviarDocumento).pipe(
    //         map((result: any) => result),
    //         catchError(this.handleError)
    //     );
    // }

    enviarDocumento(data: any) {
        return this.httpClient.post(`${environment.urlApi}proyectos/registrarEnvio`,data,{headers:this.headerMultipart()}).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }    

    registrarValidacion(rqValidarDocumento: RequestValidarDocumento) {
        return this.httpClient.post(`${environment.urlApi}proyectos/registrarValidacion`, rqValidarDocumento).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }
    
    headerMultipart():HttpHeaders{
        return new HttpHeaders({'x-multimedia':'si'});
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
                errorMessage = 'Error en el servicio de proyectos, intente ingresar al sistema nuevamente';
            } else {
                errorMessage = `Error: ${err.status}\n Mensaje: ${err.message}`;
            }
        }
        return throwError(() => {
            return errorMessage;
        });
    }
}