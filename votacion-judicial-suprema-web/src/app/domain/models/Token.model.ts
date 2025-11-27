export interface TokenResponseA {
    token: string,
    exps: number,
    refs: number
}

export interface TokenRefresh{
    token: string
}

export interface RefreshTokenResponse {
    codigo: string,
    descripcion: string,
    data: TokenRefresh,
    codigoOperacion: string
}