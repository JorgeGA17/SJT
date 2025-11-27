import { Injectable } from '@angular/core';
import { constantes } from '../../../domain/commons/constants';
import { CryptoService } from '../../security/encryption/crypto.service';
import { map, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  private readonly JWT_TOKEN = constantes.JWT_TOKEN;
  private readonly TOKEN_VALID_SEC = constantes.TOKEN_VALID_SEC;
  private readonly REFRESH_TOKEN_VALID_SEC = constantes.REFRESH_TOKEN_VALID_SEC;
  private readonly DATETIME_NEW_TOKEN = constantes.DATETIME_NEW_TOKEN;
  private readonly JWT_TOKEN_NIVEL = constantes.JWT_TOKEN_NIVEL;

  constructor(private cryptoService: CryptoService) {}
  setToken(token: string) {
    localStorage.setItem(this.JWT_TOKEN, token);
  }

  getToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  removeToken() {
    localStorage.removeItem(this.JWT_TOKEN);
  }
  //------
  /*setTokenNivel(tokenNivel: string) {
    this.cryptoService.encrypt(tokenNivel).subscribe(encrypted => {
      localStorage.setItem(this.JWT_TOKEN_NIVEL, encrypted);
    });
  }*/
  setTokenNivel(tokenNivel: string): Observable<void> {
    return this.cryptoService.encrypt(tokenNivel).pipe(
      tap(encrypted => {
        localStorage.setItem(this.JWT_TOKEN_NIVEL, encrypted);
      }),
      map(() => void 0)
    );
  }
  getTokenNivel() : Observable<string | null> {
      const tokenNi = localStorage.getItem(this.JWT_TOKEN_NIVEL);
      if (!tokenNi) return of(null);
      return this.cryptoService.decrypt(tokenNi);
  }
  removeTokenNivel() {
    localStorage.removeItem(this.JWT_TOKEN_NIVEL);
  }
  //---
  setTimeTokenValido(exp: number) {
    localStorage.setItem(this.TOKEN_VALID_SEC, exp.toString());
  }

  getTimeTokenValido(): number {
    let exp: string | null = localStorage.getItem(this.TOKEN_VALID_SEC);
    return exp ? +exp : -1;
  }
  //----
  setTimeRefreshValido(ref: number) {
    localStorage.setItem(this.REFRESH_TOKEN_VALID_SEC, ref.toString());
  }

  getTimeRefreshValido(): number {
    let ref: string | null = localStorage.getItem(this.REFRESH_TOKEN_VALID_SEC);
    return ref ? +ref : -1;
  }

  removeTimes() {
    localStorage.removeItem(this.TOKEN_VALID_SEC);
    localStorage.removeItem(this.REFRESH_TOKEN_VALID_SEC);
  }
  //-----
  setDatetimeNewToken(time: Date) {
    localStorage.setItem(this.DATETIME_NEW_TOKEN, time.toString());
  }

  getDatetimeNewToken(): string {
    let data: string | null = localStorage.getItem(this.DATETIME_NEW_TOKEN);
    return data ? data : '';
  }

  logoutSession() {
    localStorage.clear();
  }

  getDiffTimeSession(): number {
    try {
      let dateTimeNewToken: string = this.getDatetimeNewToken();
      if (dateTimeNewToken.trim() === '') {
        return -1;
      }
      let sessionInit: Date = new Date(dateTimeNewToken);
      let sessionNow: Date = new Date();
      return sessionNow.getTime() - sessionInit.getTime();
    } catch (e) {
      return -1;
    }
  }

  isTimetSessionValid(): boolean {
    // solo para el exp
    let diffSessionTime: number = this.getDiffTimeSession();
    let timeTokenValido: number = this.getTimeTokenValido(); //segundos
    //console.log(`dierencia ${diffSessionTime}, time token ${timeTokenValido}`);
    if (diffSessionTime < 0 || timeTokenValido < 0) {
      return false;
    }
    return diffSessionTime < timeTokenValido * 1000;
  }

  isTimetSessionRefreshValid(): boolean {
    let diffSessionTime: number = this.getDiffTimeSession();
    let timeTokenValido: number = this.getTimeTokenValido(); //segundos
    let timeRefreshValido: number = this.getTimeRefreshValido(); //segundos
    //console.log(`dierencia ${diffSessionTime}, time token ${timeTokenValido}, time refresh ${timeRefreshValido}`);
    if (diffSessionTime < 0 || timeRefreshValido < 0 || timeTokenValido < 0) {
      return false;
    }
    return diffSessionTime < (timeTokenValido + timeRefreshValido) * 1000;
  }
  //--
}
