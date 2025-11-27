import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MaestraColegiadosRequestParams, MaestraFallosRequestParams, MaestraSentidosRequestParams } from '../../../domain/dto/remote/MaestrasRequest.dto';
import { cleanParams } from './BaseService';
import { MaestrasColegiadoResponse, MaestrasEstadoPropectoResponse, MaestrasEstadoVotacionResponse, MaestrasFalloResponse, MaestrasSentidoResponse } from '../../../domain/dto/remote/MaestrasResponse.dto';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MaestrasService {
  constructor(
    private httpClient: HttpClient
  ) {}

  getMaestrasFallos(parametros: MaestraFallosRequestParams){
    const params = cleanParams(parametros);
    return this.httpClient.get<MaestrasFalloResponse>(`${ environment.urlApi }maestras/fallos`, { params });
  }

  getMaestrasColegiados(parametros: MaestraColegiadosRequestParams){
    const params = cleanParams(parametros);
    return this.httpClient.get<MaestrasColegiadoResponse>(`${ environment.urlApi }maestras/colegiados`, { params });
  }

  getMaestrasEstadosVotacion(){
    return this.httpClient.get<MaestrasEstadoVotacionResponse>(`${ environment.urlApi }maestras/estados-votacion`);
  }

  getMaestrasEstadosProyectos(){
    return this.httpClient.get<MaestrasEstadoPropectoResponse>(`${ environment.urlApi }maestras/estados-proyecto`);
  }

  getMaestrasSentidos(parametros: MaestraSentidosRequestParams){
    const params = cleanParams(parametros);
    return this.httpClient.get<MaestrasSentidoResponse>(`${ environment.urlApi }maestras/sentidos`);
  }

}
