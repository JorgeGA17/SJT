import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { cleanParams } from './BaseService';
import { JurisprudenciaDescargarRequestParams, JurisprudenciasRequestParams } from '../../../domain/dto/remote/JurisprudenciaRequest.dto';
import { JurisprudenciasResponse } from '../../../domain/dto/remote/JurisprudenciaResponse.dto';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class JurisprudenciaService {
  constructor(
    private httpClient: HttpClient
  ) { }

  getJurisprudencias(parametros: JurisprudenciasRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<JurisprudenciasResponse>(`${environment.urlApi}jurisprudencias`, { params });
  }

  getJurisprudenciaDescargar(parametros: JurisprudenciaDescargarRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<any>(`${environment.urlApi}jurisprudencias:descargar`, { params });
  }

}
