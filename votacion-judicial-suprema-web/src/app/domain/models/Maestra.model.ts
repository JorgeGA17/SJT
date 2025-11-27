export class TipoDocumentoModel {
    constructor(
        public codigo: string,
        public descripcion: string,
    ) { }
}

interface BaseResponse {
    codigo: string,
    descripcion: string
    codigoOperacion: string
}

export interface ListaTipoDocumentosResponse extends BaseResponse {
    data: TipoDocumentoModel[]
}

export class ValidadorModel {
    constructor(
        public aplicaCaptcha: string,
        public tokenCaptcha: string,
        public identificadorUnicoCarnet: string
    ) { }
}

