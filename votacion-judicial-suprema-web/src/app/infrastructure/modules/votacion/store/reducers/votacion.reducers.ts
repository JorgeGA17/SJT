import { createReducer, on } from "@ngrx/store";
import * as acciones from '../actions';
import * as estados from '../states';

const _cargarListaCasaciones = createReducer(estados.cargarListaCasacionesInit,
    on(acciones.cargarListaCasaciones, (state, { lista }) => ({
        ...state,
        lista: lista
    }))
);

export function cargarListaCasaciones(state:any, action:any) {
    return _cargarListaCasaciones(state, action);
}