import { BaseResponse } from './BaseResponse.dto';
 export interface VistaCausaValidacionData{
    codigoRespuesta: number,
    descripcionRespuesta: string
 }

 export interface VistaCausaValidacionResponse{
    data: VistaCausaValidacionData
 }