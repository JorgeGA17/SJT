import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { cleanParams } from './BaseService';
import { VistaCausaValidacionRequestParams, VistaCausaValidacionRequestPath, VistaCausaVotacionRequestPath } from '../../../domain/dto/remote/VistasCausaRequest.dto';
import { VistaCausaValidacionResponse } from '../../../domain/dto/remote/VistasCausaResponse.dto';
import { environment } from '../../../../environments/environment';
import { VistaCausaModel } from '../../../domain/models/VistaCausa.model';
import { GenericResponse } from '../../../domain/dto/remote/BaseResponse.dto';

@Injectable({
  providedIn: 'root'
})
export class VistasCausaService {
  constructor(
    private httpClient: HttpClient
  ) {}

  getVistaCausaValidacion(parametros: VistaCausaValidacionRequestParams, datosRuta: VistaCausaValidacionRequestPath){
        const params = cleanParams(parametros);
        return this.httpClient.get<VistaCausaValidacionResponse>(`${ environment.urlApi }vistas-causa/${datosRuta.programacion}/validacion`, { params });
  }

  postVistaCausaVotacion(datos:VistaCausaModel ,datosRuta: VistaCausaVotacionRequestPath){
        return this.httpClient.post<GenericResponse>(`${ environment.urlApi }vistas-causa/${datosRuta.programacion}/casacion/${datosRuta.distrito}/${datosRuta.provincia}/${datosRuta.distrito}/${datosRuta.unico}/${datosRuta.incidente}/votacion`, datos);
  }
  
  postVistaCausaVotacionAutomatico(datos:VistaCausaModel ,datosRuta: VistaCausaVotacionRequestPath){
        return this.httpClient.post<GenericResponse>(`${ environment.urlApi }vistas-causa/${datosRuta.programacion}/casacion/${datosRuta.distrito}/${datosRuta.provincia}/${datosRuta.distrito}/${datosRuta.unico}/${datosRuta.incidente}/automatico`, datos);
  }
}
