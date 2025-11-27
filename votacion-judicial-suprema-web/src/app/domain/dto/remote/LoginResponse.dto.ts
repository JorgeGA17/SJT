
import { PersonaModel } from '../../models/Persona.model';
import { BaseResponse } from './BaseResponse.dto';

export interface Perfil {
  codigo: string,
  descripcion: string
}

export interface Instancia{
  codigoInstancia: string,
  nombreInstancia: string,
  codigoOrganoJurisdiccional: string,
  codigoDistrito: string,
  codigoProvincia: string,
  select?:boolean
}

export interface Usuario {
  codigoUsuario: string,
  codigoDistrito: string,
  nombreDistrito: string,
  codigoProvincia: string,
  documentoIdentidad: string,
  apellidoPaterno: string,
  apellidoMaterno: string,
  nombres: string,
  instancias: Instancia[],
  perfiles: Perfil[];
  token: string;
}

export interface LoginResponse extends BaseResponse {
  data: Usuario;
}
