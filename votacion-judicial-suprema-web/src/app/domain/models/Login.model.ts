import { IconDefinition } from "@fortawesome/free-solid-svg-icons"

export class LoginRequest {
    constructor(
        public usuario: string,
        public clave: string,
        public tokenCaptcha: string,
        public aplicaCaptcha: string
    ) { }
}

// export class LoginResponse {
//     constructor(
//         public codigo: string,
//         public descripcion: string,
//         public data: Usuario,
//         public codigoOperacion: string
//     ) { }
// }

interface BaseResponse {
    codigo: string,
    descripcion: string,
    codigoOperacion: string
}

// export interface Persona {
//     idPersona: number,
//     numeroDocumento: string,
//     fechaNacimiento: string,
//     primerApellido: string,
//     segundoApellido: string,
//     nombres: string,
//     sexo: string,
//     correo: string,
//     telefono: string,
//     idTipoDocumento: string,
//     tipoDocumento: string
// }



export interface Usuario {
    codigoUsuario: string,
    dni: string,
    apellidoPaterno: string,
    apellidoMaterno: string,
    nombres: string,
    codigoDistrito: string,
    nombreDistrito: string,
    codigoProvincia: string,
    token: string
}

export interface DataUsuario {
    usuario: Usuario,
    mensaje: string,
    consultado: string
}

export interface LoginResponse extends BaseResponse {
    data: DataUsuario
}