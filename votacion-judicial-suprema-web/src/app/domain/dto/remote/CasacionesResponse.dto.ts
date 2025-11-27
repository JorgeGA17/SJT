import { CasacionDataModel } from '../../models/Casacion.model';
import { BaseResponse } from './BaseResponse.dto';
/**
 * casacion
 */
export interface CasacionesResponse extends BaseResponse{
    data: CasacionDataModel[]
}

/**
 * casacion relacionado
 */
export interface CasacionesRelacionadosResponse extends BaseResponse{
    data: CasacionDataModel[]
}

/**
 * Casacion Vistca CAusa Magistrados
 */
interface CasacionVCMagistrado{
    codigoUsuarioVocal: string,
    nombres:            string,
    iniciales:          string,
    ponente:            string,
    presidente:         string,
    nivelInstruccion:   string,
}

export interface CasacionVCMagistradosResponse extends BaseResponse{
    data: CasacionVCMagistrado[]
}

/**
 * Casacion Vista Causa Impedidos
 */
export interface CasacionVCImpedido {
    abreviatura: string,
    impedido:    string,
}

export interface CasacionVCImpedidosResponse extends BaseResponse{
    data: CasacionVCImpedido[]
}

/**
 * Casacion Vista Causa Materias
 */
export interface CasacionVCMateria {
    codigoMateria:      string,
    descripcionMateria: string,
}
export interface CasacionVCMateriasResponse extends BaseResponse{
    data: CasacionVCMateria[]
}

/**
 * casacion VistaCausa Causales
 */
export interface CasacionVCCausal {
    numeroSecuencia:    number,
    numeroSecuenciaDet: number,
    flagGrupo:          string,
    detalle:            string,
}
export interface CasacionVCCausalesResponse extends BaseResponse{
    data: CasacionVCCausal[]
}

/**
 * Casacion Vista Causa Partes Recurrentes
 */
export interface CasacionVCParteRecurrente {
    numeroSecuencia:      number,
    flagTipoParte:        string,
    nombreParte:          string,
    descripcionParte:     string,
    numeroOrden:          number,
    numeroSuborden:       number,
    flagRecurrente:       string,
    abreviatura:          string,
    numeroSentido:        number,
    numeroSecuenciaParte: number,
    numeroVotacion:       number,
    codigoSentido:        string,
    codigoFallo:          number,
    observacion:          string,
    flagDiscordia:        string,
    descripcionSentido:   string,
    descripcionFallo:     string,
    flagPublicado:        string,
}


export interface CasacionVCPartesRecurrentesResponse extends BaseResponse {
    data: CasacionVCParteRecurrente[]
}

/**
 * Casacion Vista Causa Magistrados Discordia
 */
export interface CasacionVCMagistradoDiscordia {
    codigoUsuarioVocal: string,
    nombres:            string,
    codigoUsuario:      string,
    iniciales:          string,
    flagDiscordia:      string,
    idVotoDiscordia:    null,
}

export interface CasacionVCMagistradosDiscordiaResponse extends BaseResponse {
    data: CasacionVCMagistradoDiscordia[]
}

/**
 * Casacion Vista Causa Apuntes
 */
export interface CasacionVCApuntes {
    apuntes: string,
}

export interface CasacionVCApuntesResponse extends BaseResponse {
    data: CasacionVCApuntes[]
}

/**
 * Casacion Vista Causa Documentos
 */
export interface CasacionVCDocumento {
    nombreEntidad:    string,
    nombreRecurso:    string,
    uuid:             string,
    idJurisprudencia: number,
}
export interface CasacionVCDocumentosResponse extends BaseResponse {
    data: CasacionVCDocumento[]
}