import { BaseResponse } from './BaseResponse.dto';

/**
 * Maestra Fallos
 */
interface MaestraFallo {
    codigoFallo: string,
    descripcionFallo: string
}

export interface MaestrasFalloResponse extends BaseResponse {
  data: MaestraFallo[];
}

/**
 * Maestra Colegiados
 */
interface MaestraColegiado {
    codigo: string,
    apellidoPaterno: string,
    apellidoMaterno: string,
    nombres: string
}

export interface MaestrasColegiadoResponse extends BaseResponse {
  data: MaestraColegiado[];
}

/**
 * Maestra Estado Votacion
 */
interface MaestraEstadoVotacion {
    codigoEstado: string,
    descripcion: string
}

export interface MaestrasEstadoVotacionResponse extends BaseResponse {
  data: MaestraEstadoVotacion[];
}

/**
 * Maestras Estado de Proyecto
 */
interface MaestraEstadoPropecto{
    id: number,
    descripcion: string,
    activo: string,
    icono: string | null
}
export interface MaestrasEstadoPropectoResponse extends BaseResponse {
  data: MaestraEstadoPropecto[];
}

/**
 * Maestras sentidos
 */
interface MaestraSentido{
    codigoSentido: string,
    descripcionSentido: string
}
export interface MaestrasSentidoResponse extends BaseResponse {
  data: MaestraSentido[];
}