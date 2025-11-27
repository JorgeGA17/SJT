import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AppVotacionesState} from './votaciones.reducers';

export const getLayout = createFeatureSelector<AppVotacionesState>('VotacionesModule');

export const getDetalleCasacion= createSelector(getLayout, (state: AppVotacionesState) => {
  if (state.cargarDetalleCasacion) {
    return state.cargarDetalleCasacion.detalle;
  } else {
    return null;
  }
});

