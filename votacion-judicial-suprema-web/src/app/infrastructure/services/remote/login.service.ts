import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { LoginRequest } from '../../../domain/dto/remote/LoginRequest.dto';
import { LoginResponse } from '../../../domain/dto/remote/LoginResponse.dto';

import { LocalStorageService } from '../local/local-storage.service';
import { OpcionesRequest } from '../../../domain/dto/remote/OpcionesRequest.dto';
import { OpcionesResponse } from '../../../domain/dto/remote/OpcionesResponse.dto';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) {}
  login(user: LoginRequest) {
    return this.httpClient.post<LoginResponse>(`${environment.urlApi}authenticate/login`, user);
  }

  opciones(req: OpcionesRequest) {
    return this.httpClient.post<OpcionesResponse>(`${environment.urlApi}authenticate/opciones`, req);
  }
}
