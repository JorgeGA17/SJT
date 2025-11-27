import { Injectable } from '@angular/core';
import { MaestrasService } from '../infrastructure/services/remote/maestras-service';
import { MaestraColegiadosRequestParams, MaestraFallosRequestParams, MaestraSentidosRequestParams } from '../domain/dto/remote/MaestrasRequest.dto';
import { MaestrasColegiadoResponse, MaestrasEstadoPropectoResponse, MaestrasEstadoVotacionResponse, MaestrasFalloResponse, MaestrasSentidoResponse } from '../domain/dto/remote/MaestrasResponse.dto';
import { constantes, mensajes } from '../domain/commons/constants';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MaestrasUseCases {
  constructor(private maestrasService: MaestrasService){}

  listarFallos(parametros: MaestraFallosRequestParams) {
    return this.maestrasService.getMaestrasFallos(parametros).pipe(
      map((response: MaestrasFalloResponse) => {
        if (response.codigo === constantes.RES_COD_EXITO) {
          if (!response.data) {
            response.codigo = constantes.RES_COD_NO_DATA;
            response.descripcion = mensajes.MSG_RESP_NO_DATA;
          }
        }
        return response;
      })
    );
  }

  listarColegiados(parametros: MaestraColegiadosRequestParams) {
    return this.maestrasService.getMaestrasColegiados(parametros).pipe(
      map((response: MaestrasColegiadoResponse) => {
        if (response.codigo === constantes.RES_COD_EXITO) {
          if (!response.data) {
            response.codigo = constantes.RES_COD_NO_DATA;
            response.descripcion = mensajes.MSG_RESP_NO_DATA;
          }
        }
        return response;
      })
    );
  }

  listarEstadosVotacion() {
    return this.maestrasService.getMaestrasEstadosVotacion().pipe(
      map((response: MaestrasEstadoVotacionResponse) => {
        if (response.codigo === constantes.RES_COD_EXITO) {
          if (!response.data) {
            response.codigo = constantes.RES_COD_NO_DATA;
            response.descripcion = mensajes.MSG_RESP_NO_DATA;
          }
        }
        return response;
      })
    );
  }

  listarEstadosProyectos() {
    return this.maestrasService.getMaestrasEstadosProyectos().pipe(
      map((response: MaestrasEstadoPropectoResponse) => {
        if (response.codigo === constantes.RES_COD_EXITO) {
          if (!response.data) {
            response.codigo = constantes.RES_COD_NO_DATA;
            response.descripcion = mensajes.MSG_RESP_NO_DATA;
          }
        }
        return response;
      })
    );
  }

  listarSentidos(parametros: MaestraSentidosRequestParams) {
    return this.maestrasService.getMaestrasSentidos(parametros).pipe(
      map((response: MaestrasSentidoResponse) => {
        if (response.codigo === constantes.RES_COD_EXITO) {
          if (!response.data) {
            response.codigo = constantes.RES_COD_NO_DATA;
            response.descripcion = mensajes.MSG_RESP_NO_DATA;
          }
        }
        return response;
      })
    );
  }
  
}
