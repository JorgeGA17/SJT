import { BaseResponse } from './BaseResponse.dto';

export interface Jurisprudencia {
    nombreRecuso:  string;
    uuid:          string;
    origenRecurso: string;
}

export interface JurisprudenciasResponse{
    data: Jurisprudencia[]
}