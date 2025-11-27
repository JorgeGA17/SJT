import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, filter, switchMap, take } from 'rxjs/operators';
import { Router } from '@angular/router';
//import { environment } from 'src/environments/environment';
import { AuthService } from '../services/remote/autenticacion/auth.service';
//import Swal from 'sweetalert2';
import { RefreshTokenResponse } from 'src/app/domain/models/Token.model';
import { constantes } from 'src/app/constants';

@Injectable()
export class ErrorCredencialesInterceptor implements HttpInterceptor {

  private refreshTokenInProgress = false;
  private refreshTokenSubject: Subject<any> = new BehaviorSubject<any>(null);
  constructor(private route: Router, public authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      return next.handle(request).pipe(catchError(err => {
          if ((err.status === 401 || err.status === 403) && !request.url.endsWith('api/authenticate') ) {
            if(request.url.includes('seguridad/refresh')){
              return throwError(()=>err);
            }
            else{
              let usuarioLocal:string|null =this.authService.getToken()
              return this.handle401Error(request, next, usuarioLocal);
            }
          }
          else{
            return throwError(()=>err);
          }
      }));
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler, token: any) {
    if (!this.refreshTokenInProgress) {
      this.refreshTokenInProgress = true;
      this.refreshTokenSubject.next(null);
      let newDAte:Date = new Date();
      return this.authService.refreshToken(token).pipe(
        switchMap((data: any) => {
          let dataRefresh:RefreshTokenResponse = data;
          if(dataRefresh.codigo===constantes.RESPONSE_COD_EXITO){
            this.authService.setToken(dataRefresh.data.token);
            this.authService.setDatetimeNewToken(newDAte);
            this.refreshTokenInProgress = false;
            this.refreshTokenSubject.next(data.token);
            return next.handle(this.injectToken(request));
          }
          else{
            return throwError(() => {
              return dataRefresh.descripcion;
            });
          }
        }),
        catchError(err => {
          if(err.status === 401 || err.status === 403){
            this.logOutRefresh();
          }
          this.refreshTokenInProgress = false;
          return throwError(()=>err);
        }));

    } 
    else {
      return this.refreshTokenSubject.pipe(
        filter(token => token != null),
        take(1),
        switchMap(jwt => {
          return next.handle(this.injectToken(request));
        }));
    }
  }

  injectToken(request: HttpRequest<any>) {
    const token = this.authService.getToken(); 
    if (request.headers.get('x-multimedia') === 'si') {
      return request.clone({
          setHeaders: {
              Authorization: `Bearer ${token}`
          }
      });
    }    
    return request.clone({
        setHeaders: {
            'Content-Type':  'application/json',
            Authorization: `Bearer ${ token }`
        }
    });
  }

  logOutRefresh():void{
    this.authService.logoutSession();
    this.route.navigate(['/autenticacion/login']);
  }

}