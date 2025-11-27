/**
 * Casacion
 */
export interface CasacionesRquestPath{
    distrito: string,
    provincia: string,
    instancia: string,
}

export interface CasacionesRquestParams{
    fecha_inicio: string,
    fecha_fin: string
}

/**
 * casaciones relacionados
 */
export interface CasacionesRelacionadosRquestPath{
    distrito:   string,
    provincia:  string,
    instancia:  string,
    unico:      string,
    incidente:  string
}

/**
 * Casacion Vista Causa Magistrados
 */
export interface CasacionVCMagistradosRequestParms{
    numero_grupo: string,
    numero_secuencia: string,
    numero_conformacion: string
}

export interface CasacionVCMagistradosRequestPath{
    programacion:string
}

/**
 * Casacion Vista Causa Impedidos
 */
export interface CasacionVCImpedidosRequestPath{
    distrito:       string,
    provincia:      string,
    instancia:      string,
    unico:          string,
    incidente:      string,
    programacion:   string
}

export interface CasacionVCImpedidosRequestParams{
    fecha_ingreso:  string
}

/**
 * Casacion Vista Causa Materias
 */
export interface CasacionVCMateriasRequestPath{
    unico:          string,
    incidente:      string,
    programacion:   string
}


/**
 * Casacion Vista Causa Causales
 */
export interface CasacionVCCausalesRequestPath{
    unico:          string,
    incidente:      string,
    programacion:   string
}

/**
 * Casacion Vista Causa Partes Recurrentes
 */
export interface CasacionVCPartesRecurrentesRequestPath{
    unico:          string,
    incidente:      string,
    programacion:   string
}

export interface CasacionVCPartesRecurrentesRequestParams{
    flag_discordia:         string,
    numero_sentido:         string,
    numero_votacion:        string
}

/**
 * Casacion Vista Causa Magistrado Discordia
 */
export interface CasacionVCMagistradosDiscordiaRequestPath{
    unico:          string,
    incidente:      string,
    programacion:   string
}

export interface CasacionVCMagistradosDiscordiaRequestParams{
    numero_sentido:         string,
    numero_votacion:        string,
    numero_secuencia_parte: string,
    codigo_vocal_ponente:   string
}

/**
 * Casacion Vista Causa Apuntes
 */
export interface CasacionVCApuntesRequestPath{
    unico:          string,
    incidente:      string
}

export interface CasacionVCApuntesRequestParams{
    numero_sentido:             string,
    codigo_vocal_usuario:       string
}

/**
 * Casacion Visat Causa Documentos
 */
export interface CasacionVCDocumentosRequestPath{
    unico:          string,
    incidente:      string,
    programacion:   string
}
