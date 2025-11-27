import { ActionReducerMap } from '@ngrx/store';

//----------- general - shared --------
import * as reducers from './reducers';
import * as states from './states/casacion.states';

export interface AppVotacionesState {
  cargarDetalleCasacion: states.cargarDetalleCasacion;
}

export const appVotacionesReducers: ActionReducerMap<AppVotacionesState> = {
  cargarDetalleCasacion: reducers.cargarDetalleCasacion,
};
