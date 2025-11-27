import { HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, debounce, throwError } from 'rxjs';

export function handleError(err: HttpErrorResponse): Observable<never> {
  let errorMessage = '';
  if (err.error instanceof ErrorEvent) {
    errorMessage = err.error.message;
  } else {
    if (err.status === 0) {
      return throwError(() => {
        return err.message;
      });
    }

    if (!err.status) {
      return throwError(() => {
        return err;
      });
    }

    if (err.status === 401 || err.status === 403) {
      errorMessage = 'Error en autoriaztion, intente ingresar al sistema nuevamente';
    } else {
      errorMessage = `Error: ${err.status}\n Mensaje: ${err.message}`;
    }
  }
  return throwError(() => {
    return errorMessage;
  });
}

export function toParams(dataObjeto: any): HttpParams {
  let params = new HttpParams();
  Object.keys(dataObjeto).forEach((key) => {
    if (dataObjeto[key] != null && dataObjeto[key] !== '') {
      params = params.set(key, dataObjeto[key]);
    }
  });
  return params;
}

export function cleanParams<T extends Record<string, any>>(obj: T): Record<string, any> {
  return Object.entries(obj).reduce((acc, [key, value]) => {
    // Eliminar valores nulos, undefined, strings vacíos y arrays vacíos
    if (value !== null && 
        value !== undefined && 
        value !== '' && 
        !(Array.isArray(value) && value.length === 0)) {
      acc[key] = value;
    }
    return acc;
  }, {} as Record<string, any>);
}

export function headerMultipart(esPublic: string = 'no'): HttpHeaders {
  return new HttpHeaders({ 'x-multimedia': 'si', 'x-publico': esPublic });
}
