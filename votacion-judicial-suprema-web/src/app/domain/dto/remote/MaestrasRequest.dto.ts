export interface MaestraFallosRequestParams{
    codigo_especialidad?:string,
    codigo_abreviatura?:string
}

export interface MaestraColegiadosRequestParams{
    codigo_distrito?: string,
    codigo_provincia?: string,
    codigo_instancia?: string
}

export interface MaestraSentidosRequestParams{
    codigo_distrito?: string,
    codigo_provincia?: string,
    codigo_instancia?: string
}
