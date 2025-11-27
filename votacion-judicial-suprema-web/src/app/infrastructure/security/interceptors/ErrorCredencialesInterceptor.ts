import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, filter, switchMap, take } from 'rxjs/operators';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../services/local/local-storage.service';
import { AuthService } from '../../services/remote/auth.service';
import { RefreshTokenResponse } from '../../../domain/dto/remote/RefreshTokenResponse.dto';
import { constantes } from '../../../domain/commons/constants';
import { handleError } from '../../services/remote/BaseService';

@Injectable()
export class ErrorCredencialesInterceptor implements HttpInterceptor {
  private refreshTokenInProgress = false;
  private refreshTokenSubject: Subject<any> = new BehaviorSubject<any>(null);
  constructor(
    private route: Router,
    private authService: AuthService,
    public localStorage: LocalStorageService
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err) => {
        if ((err.status === 401 || err.status === 403) && !request.url.endsWith('api/authenticate')) {
          if (request.url.includes('seguridad/refresh')) {
            return throwError(() => err);
          } else {
            let usuarioLocal: string | null = this.localStorage.getToken();
            return this.handle401Error(request, next, usuarioLocal);
          }
        } else {
          return handleError(err);
        }
      })
    );
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler, token: any) {
    if (!this.refreshTokenInProgress) {
      this.refreshTokenInProgress = true;
      this.refreshTokenSubject.next(null);
      let newDAte: Date = new Date();
      return this.authService.refreshToken(token).pipe(
        switchMap((dataRefresh: RefreshTokenResponse) => {
          if (dataRefresh.codigo === constantes.RES_COD_EXITO) {
            this.localStorage.setToken(dataRefresh.data.token);
            this.localStorage.setDatetimeNewToken(newDAte);
            //this.authService.setTokenAdmin(token.tokenAdmin);
            this.refreshTokenInProgress = false;
            this.refreshTokenSubject.next(dataRefresh.data.token); //----------
            return next.handle(this.injectToken(request));
          } else {
            return throwError(() => {
              return dataRefresh.descripcion;
            });
          }
        }),
        catchError((err) => {
          //this.refreshTokenInProgress = false;
          if (err.status === 401 || err.status === 403) {
            this.logOutRefresh();
            //console.log("error refresh",err);
          }
          this.refreshTokenInProgress = false;
          return handleError(err);
        })
      );
    } else {
      return this.refreshTokenSubject.pipe(
        filter((token) => token != null),
        take(1),
        switchMap((jwt) => {
          return next.handle(this.injectToken(request));
        })
      );
    }
  }

  injectToken(request: HttpRequest<any>) {
    //console.log("inyectamdo");
    const token = this.localStorage.getToken();
    // const tokenAdmin = this.authService.getTokenAdmin();
    return request.clone({
      setHeaders: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
  }

  logOutRefresh(): void {
    //Swal.fire('Atención!', 'Error en autenticación, intente ingresar al sistema nuevamente', 'error');
    this.route.navigate(['/autenticacion/login']);
  }
}
