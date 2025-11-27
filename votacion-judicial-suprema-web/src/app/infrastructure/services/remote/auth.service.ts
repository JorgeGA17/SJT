import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { tokenResponse } from '../../../domain/dto/remote/tokenResponse.dto';
import { RefreshTokenResponse } from '../../../domain/dto/remote/RefreshTokenResponse.dto';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private httpClient: HttpClient
  ) {}

  recuperarTokenAutorization() {
    return this.httpClient.post<tokenResponse>(`${environment.urlApi}api/authenticate`, null, {
      headers: new HttpHeaders({
        username: environment.usuarioConsumo,
        password: environment.claveUsuarioConsumo,
        codigoRol: environment.codigoRol,
      }),
      responseType: 'json',
    });
  }

  refreshToken(token: string) {
    return this.httpClient.get<RefreshTokenResponse>(`${environment.urlApi}seguridad/refresh?token=${token}`);
  }
}
