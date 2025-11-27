import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { cleanParams } from './BaseService';
import { ReporteEstadoVotacionDataRequestParams, ReporteEstadoVotacionDescargaRequestParams, ReporteGeneralDataRequestParams, ReporteGeneralDescargarRequestParams } from '../../../domain/dto/remote/ReportesRequest.dto';
import { ReporteEstadoVotacionDataResponse, ReporteGeneralDataResponse } from '../../../domain/dto/remote/ReportesResponse.dto';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportesService {
  constructor(
    private httpClient: HttpClient
  ) { }

  getReporteEstadoVotacionData(parametros: ReporteEstadoVotacionDataRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<ReporteEstadoVotacionDataResponse>(`${environment.urlApi}/reportes/estado-votacion:data`, { params });
  }

  getReporteEstadoVotacionDescarga(parametros: ReporteEstadoVotacionDescargaRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<any>(`${environment.urlApi}/reportes/estado-votacion:descargar`, { params });
  }

  getReporteGeneralData(parametros: ReporteGeneralDataRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<ReporteGeneralDataResponse>(`${environment.urlApi}/reportes/general:data`, { params });
  }

  getReporteGeneralDescargar(parametros: ReporteGeneralDescargarRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<any>(`${environment.urlApi}/reportes/general:descargar`, { params });
  }

}
