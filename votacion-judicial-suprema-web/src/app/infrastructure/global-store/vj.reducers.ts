import { ActionReducerMap } from '@ngrx/store';

//----------- reducers ----------
import * as reducersGeneral from './main/reducers';
//------------ states -----------
import * as statesGeneral from './main/states/general.states';

export interface AppVjState {
  //--- general
  mostrarCargando: statesGeneral.mostrarCargando;
}

export const appVjReducers: ActionReducerMap<AppVjState> = {
  //--- general
  mostrarCargando: reducersGeneral.mostrarCargando,
};
