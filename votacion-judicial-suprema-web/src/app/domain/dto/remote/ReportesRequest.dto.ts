export interface ReporteEstadoVotacionDataRequestParams{
    codigo_distrito:        string,
    codigo_provincia:       string,
    codigo_instancia:       string,
    id_estado_votacion:     string,
    fecha_inicio:           string,
    fecha_fin:              string
}

export interface ReporteEstadoVotacionDescargaRequestParams{
    codigo_distrito:        string,
    codigo_provincia:       string,
    codigo_instancia:       string,
    id_estado_votacion:     string,
    fecha_inicio:           string,
    fecha_fin:              string,
    tipo_reporte:           string
}

export interface ReporteGeneralDataRequestParams{
    codigo_distrito:        string,
    codigo_provincia:       string,
    codigo_instancia:       string,
    usuario_responsable:    string,
    id_estado_votacion:     string,
    id_estado_proyecto:     string,
    fecha_inicio:           string,
    fecha_fin:              string
}

export interface ReporteGeneralDescargarRequestParams{
    codigo_distrito:        string,
    codigo_provincia:       string,
    codigo_instancia:       string,
    usuario_responsable:    string,
    id_estado_votacion:     string,
    id_estado_proyecto:     string,
    fecha_inicio:           string,
    fecha_fin:              string,
    tipo_reporte:           string
}