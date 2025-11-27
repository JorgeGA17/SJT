export interface VistaCausaValidacionRequestPath{
    programacion: string
}

export interface VistaCausaValidacionRequestParams{
    codigo_distrito: string,
    codigo_provincia: string,
    codigo_instancia: string,
    numero_grupo_voto: string,
    numero_secuencia_voto: string,
    numero_conformacion_voto: string
}


export interface VistaCausaVotacionRequestPath{
    programacion: string,
    distrito: string,
    provincia: string,
    instancia: string,
    unico: string,
    incidente: string
}


