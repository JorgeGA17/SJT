import { ProyectoModel } from '../../models/Proyectos.model';
import { BaseResponse } from './BaseResponse.dto';

export interface ProyectosResponse extends BaseResponse{
    data: ProyectoModel[]
}

export interface ProyectosRelacionadosResponse extends BaseResponse{
    data: ProyectoModel[]
}

export interface ProyectosPendientesResponse extends BaseResponse{
    data: number
}