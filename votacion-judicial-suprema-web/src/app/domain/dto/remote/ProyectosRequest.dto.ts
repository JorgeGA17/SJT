export interface ProyectosRequestParams{
    codigo_distrito: string,
    codigo_provincia: string,
    codigo_instancia: string,
    usuario_responsable: string,
    id_estado: number,
    fecha_inicio?: string,
    fecha_fin?: string
}

export interface ProyectosRelacionadosRequestParams{
    numero_unico:       string,
    numero_incidente:   string,
    numero_sentido:     string,
    numero_votacion:    string,
    usuario_responsable:string,
}

export interface ProyectosPendientesRequestParams{
    usuario_responsable:    string,
    id_estado:              string
}

export interface ProyectosValidadosRequestParams{
    codigo_distrito:        string,
    codigo_provincia:       string,
    codigo_instancia:       string,
    usuario_responsable:    string,
    id_estado:              number,
    fecha_inicio:           string,
    fecha_fin:              string
}

export interface ProyectoValidarRequestPath{
    idProyecto:     number,
    usuario:        string
}

export interface ProyectoValidarRequest{
    numeroValidado:     number,
    observacion:        string
}

