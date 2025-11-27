import { createReducer, on } from '@ngrx/store';
import * as acciones from '../actions';
import * as estados from '../states';

const _cargarDetalleCasacion = createReducer(
  estados.cargarDetalleCasacionInit,
  on(acciones.cargarDetalleCasacion , (state, { detalle }) => ({
    ...state,
    detalle: detalle,
  }))
);

export function cargarDetalleCasacion (state: any, action: any) {
  return _cargarDetalleCasacion (state, action);
}


