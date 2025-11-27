import { CasacionDataModel } from "../../models/Casacion.model";

export interface DiaCalendario {
  fecha: Date;
  esDelMesActual: boolean;
  esHoy: boolean;
  tieneEventos: boolean;
  eventos: CasacionDataModel[];
}