import { BaseResponse } from './BaseResponse.dto';

export interface ReporteEstadoVotacionData{
    fechaProgramacion: Date;
    ponente:           null;
    nivelInstruccion:  null;
    estadoVoto:        string;
    tipoParte:         string;
    sentido:           null;
    fallo:             null;
    numeroExpediente:  string;
    recurrente:        string;
}

export interface ReporteEstadoVotacionDataResponse extends BaseResponse{
    data:   ReporteEstadoVotacionData[]
}

export interface ReporteGeneralData {
    fechaProgramacion:            Date;
    ponente:                      null;
    estadoVoto:                   string;
    tipoParte:                    string;
    sentido:                      null;
    fallo:                        null;
    anotacion:                    null;
    responsableProyecto:          null;
    estadoProyecto:               string;
    fechaEnvio:                   null;
    magistradosPendientesValidar: string;
    idProyecto:                   null;
    numeroExpediente:             string;
    recurrente:                   string;
}

export interface ReporteGeneralDataResponse extends BaseResponse{
    data:   ReporteGeneralData[]
}