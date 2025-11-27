import { Injectable } from '@angular/core';
import { AuthService } from '../infrastructure/services/remote/auth.service';
import { LocalStorageService } from '../infrastructure/services/local/local-storage.service';
import { catchError, forkJoin, map, Observable, of, switchMap, tap, throwError } from 'rxjs';
import { tokenResponse } from '../domain/dto/remote/tokenResponse.dto';
import { tokenNiveles } from '../domain/commons/constants';

@Injectable({
  providedIn: 'root',
})
export class AuthUseCasesService {
  constructor(
    private authService: AuthService,
    private localStorageService: LocalStorageService
  ) {}

  getTokenAuth(): Observable<tokenResponse> {
    const newDAte: Date = new Date();
    return this.authService.recuperarTokenAutorization().pipe(
      /*map((response: tokenResponse) => {
        if (response.token) {
          this.localStorageService.setDatetimeNewToken(newDAte);
          this.localStorageService.setToken(response.token);
          this.localStorageService.setTokenNivel(tokenNiveles.NIVEL_AUTH);
          this.localStorageService.setTimeTokenValido(response.exps);
          this.localStorageService.setTimeRefreshValido(response.refs);
        }
        return response;
      })*/
      switchMap((response: tokenResponse) => {
        if (!response.token) {
          return of(response);
        }
        return forkJoin({
          tokenNivel: this.localStorageService.setTokenNivel(tokenNiveles.NIVEL_AUTH).pipe(
            catchError(err => {
              return of(response);
            })
          ),
        }).pipe(
          tap(() => {
            this.localStorageService.setDatetimeNewToken(newDAte);
            this.localStorageService.setToken(response.token);
            this.localStorageService.setTimeTokenValido(response.exps);
            this.localStorageService.setTimeRefreshValido(response.refs);
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
