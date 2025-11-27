import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { cleanParams } from './BaseService';
import { ProyectosPendientesRequestParams, ProyectosRelacionadosRequestParams, ProyectosRequestParams, ProyectosValidadosRequestParams, ProyectoValidarRequest, ProyectoValidarRequestPath } from '../../../domain/dto/remote/ProyectosRequest.dto';
import { ProyectosPendientesResponse, ProyectosResponse } from '../../../domain/dto/remote/ProyectosResponse.dto';
import { environment } from '../../../../environments/environment';
import { GenericResponse } from '../../../domain/dto/remote/BaseResponse.dto';

@Injectable({
  providedIn: 'root'
})
export class ProyectosService {
  constructor(
    private httpClient: HttpClient
  ) { }
  
  getProyectos(parametros: ProyectosRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<ProyectosResponse>(`${environment.urlApi}proyectos`, { params });
  }

  getProyectosRelacionados(parametros: ProyectosRelacionadosRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<ProyectosResponse>(`${environment.urlApi}proyectos/relacionados`, { params });
  }

  getProyectosPendientes(parametros: ProyectosPendientesRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<ProyectosPendientesResponse>(`${environment.urlApi}proyectos/pendientes`, { params });
  }

  getProyectosValidados(parametros: ProyectosValidadosRequestParams) {
    const params = cleanParams(parametros);
    return this.httpClient.get<ProyectosResponse>(`${environment.urlApi}proyectos/validados`, { params });
  }

  postProyectoValidar(data: ProyectoValidarRequest, datosruta: ProyectoValidarRequestPath){
    return this.httpClient.post<GenericResponse>(`${environment.urlApi}proyectos/${datosruta.idProyecto}/validacion/${datosruta.usuario}`, data );
  }

  postProyectoVotar(data: FormData, idProyecto: number){
    return this.httpClient.post<GenericResponse>(`${environment.urlApi}proyectos/${idProyecto}/voto/`, data );
  }




}
