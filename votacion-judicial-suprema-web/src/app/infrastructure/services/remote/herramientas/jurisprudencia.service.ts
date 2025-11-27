import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from "rxjs";
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class JurisprudenciaService {
    
    constructor(private httpClient: HttpClient, private route: Router) { }

    buscarJurisprudencia(numeroCas: string, tipoBusqueda : string) {
        return this.httpClient.get(`${environment.urlApi}buscarJurisprudencia?expediente=${numeroCas}&source=${tipoBusqueda}`).pipe(
            map((result: any) => result),
            catchError(this.handleError)
        );
    }

    visualizarPDF(uuid: string, origen: string) {
        return this.httpClient.get(`${environment.urlApi}visualizarPdf?origen=${origen}&uuid=${uuid}`).pipe(
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
                errorMessage = 'Error en el servicio de jurisprudencia, intente ingresar al sistema nuevamente';
            } else {
                errorMessage = `Error: ${err.status}\n Mensaje: ${err.message}`;
            }
        }
        return throwError(() => {
            return errorMessage;
        });
    }
}