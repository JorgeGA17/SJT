import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { cleanParams } from './BaseService';
import { environment } from '../../../../environments/environment';
import { CasacionesRelacionadosRquestPath, CasacionesRquestParams, CasacionesRquestPath, CasacionVCApuntesRequestParams, CasacionVCApuntesRequestPath, CasacionVCCausalesRequestPath, CasacionVCDocumentosRequestPath, CasacionVCImpedidosRequestParams, CasacionVCImpedidosRequestPath, CasacionVCMagistradosDiscordiaRequestParams, CasacionVCMagistradosDiscordiaRequestPath, CasacionVCMagistradosRequestParms, CasacionVCMagistradosRequestPath, CasacionVCMateriasRequestPath, CasacionVCPartesRecurrentesRequestParams, CasacionVCPartesRecurrentesRequestPath } from '../../../domain/dto/remote/CasacionesRequest.dto';
import { CasacionesResponse, CasacionVCApuntesResponse, CasacionVCCausalesResponse, CasacionVCDocumentosResponse, CasacionVCImpedidosResponse, CasacionVCMagistradosDiscordiaResponse, CasacionVCMagistradosResponse, CasacionVCMateriasResponse, CasacionVCPartesRecurrentesResponse } from '../../../domain/dto/remote/CasacionesResponse.dto';

@Injectable({
  providedIn: 'root'
})
export class CasacionesService {
  constructor(
    private httpClient: HttpClient
  ) { }

  /**
   * Casaciones
   * @param parametros 
   * @param datosRuta 
   * @returns 
   */
  getCasaciones(parametros: CasacionesRquestParams, datosRuta: CasacionesRquestPath) {
    const params = cleanParams(parametros);
    return this.httpClient.get<CasacionesResponse>(`${environment.urlApi}casaciones/${datosRuta.distrito}/${datosRuta.provincia}/${datosRuta.instancia}`, { params });
  }

  /**
   *  casaciones relacionados
   * @param datosRuta 
   * @returns 
   */
  getCasacionesRelacionados(datosRuta: CasacionesRelacionadosRquestPath) {
    return this.httpClient.get<CasacionesResponse>(`${environment.urlApi}casaciones/${datosRuta.distrito}/${datosRuta.provincia}/${datosRuta.incidente}/${datosRuta.unico}/${datosRuta.incidente}`);
  }

  /**
   * Casaciones Vista Causa Magistrados
   * @param parametros 
   * @param datosRuta 
   * @returns 
   */
  getCasacionVCMagistrados(parametros: CasacionVCMagistradosRequestParms, datosRuta: CasacionVCMagistradosRequestPath) {
    const params = cleanParams(parametros);
    return this.httpClient.get<CasacionVCMagistradosResponse>(`${environment.urlApi}casaciones/vistas-causa/${datosRuta.programacion}/magistrados`, { params });
  }

  /**
   * Casaciones Vista Causa Impedidos
   * @param parametros 
   * @param datosRuta 
   * @returns 
   */
  getCasacionVCImpedidos(parametros: CasacionVCImpedidosRequestParams, datosRuta: CasacionVCImpedidosRequestPath) {
    const params = cleanParams(parametros);
    return this.httpClient.get<CasacionVCImpedidosResponse>(`${environment.urlApi}casaciones/${datosRuta.distrito}/${datosRuta.provincia}/${datosRuta.incidente}/${datosRuta.unico}/${datosRuta.incidente}/vistas-causa/${datosRuta.programacion}/impedidos`, { params });
  }

  /**
   * Casacion Vista Causa Materias
   * @param datosRuta 
   * @returns 
   */
  getCasacionVCMaterias(datosRuta: CasacionVCMateriasRequestPath) {
    return this.httpClient.get<CasacionVCMateriasResponse>(`${environment.urlApi}casaciones/${datosRuta.unico}/${datosRuta.incidente}/vistas-causa/${datosRuta.programacion}/materias`);
  }

  /**
   * Casacion vista Causa Causal
   * @param datosRuta 
   * @returns 
   */
  getCasacionVCCausales(datosRuta: CasacionVCCausalesRequestPath) {
    return this.httpClient.get<CasacionVCCausalesResponse>(`${environment.urlApi}casaciones/${datosRuta.unico}/${datosRuta.incidente}/vistas-causa/${datosRuta.programacion}/causales`);
  }

  /**
   * Casacion Vista Causa Partes Recurrentes
   * @param parametros 
   * @param datosRuta 
   * @returns 
   */
  getCasacionVCPartesRecurrentes(parametros: CasacionVCPartesRecurrentesRequestParams, datosRuta: CasacionVCPartesRecurrentesRequestPath) {
    const params = cleanParams(parametros);
    return this.httpClient.get<CasacionVCPartesRecurrentesResponse>(`${environment.urlApi}casaciones/${datosRuta.unico}/${datosRuta.incidente}/vistas-causa/${datosRuta.programacion}/partes-recurrentes`, { params });
  }

  /**
   * Casacion Vista Causa Magistrados Discordia
   * @param parametros 
   * @param datosRuta 
   * @returns 
   */
  getCasacionVCMagistradosDiscordia(parametros: CasacionVCMagistradosDiscordiaRequestParams, datosRuta: CasacionVCMagistradosDiscordiaRequestPath) {
    const params = cleanParams(parametros);
    return this.httpClient.get<CasacionVCMagistradosDiscordiaResponse>(`${environment.urlApi}casaciones/${datosRuta.unico}/${datosRuta.incidente}/vistas-causa/${datosRuta.programacion}/magistrados-discordia`, { params });
  }

  /**
   * Casacion Vista Causa Apuntes
   * @param parametros 
   * @param datosRuta 
   * @returns 
   */
  getCasacionVCApuntes(parametros: CasacionVCApuntesRequestParams, datosRuta: CasacionVCApuntesRequestPath) {
    const params = cleanParams(parametros);
    return this.httpClient.get<CasacionVCApuntesResponse>(`${environment.urlApi}casaciones/${datosRuta.unico}/${datosRuta.incidente}/vistas-causa/apuntes`, { params });
  }

  /**
   * Casacion Vista Causa Documentos
   * @param datosRuta 
   * @returns 
   */
  getCasacionVCDocumentos(datosRuta: CasacionVCDocumentosRequestPath) {
    return this.httpClient.get<CasacionVCDocumentosResponse>(`${environment.urlApi}casaciones/${datosRuta.unico}/${datosRuta.incidente}/vistas-causa/${datosRuta.programacion}/documentos`);
  }

}
