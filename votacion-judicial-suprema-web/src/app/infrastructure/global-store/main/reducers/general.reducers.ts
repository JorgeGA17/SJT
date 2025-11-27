import { createReducer, on } from '@ngrx/store';
import * as acciones from '../actions';
import * as estados from '../states';

/**
 * Manejador de acciones para recuperar token con autenticación
 */
const _mostrarCargando = createReducer(
  estados.mostrarCargandoInit,
  on(acciones.mostrarCargando, (state, { estado }) => ({
    ...state,
    estado: estado,
  }))
);

export function mostrarCargando(state: any, action: any) {
  return _mostrarCargando(state, action);
}

