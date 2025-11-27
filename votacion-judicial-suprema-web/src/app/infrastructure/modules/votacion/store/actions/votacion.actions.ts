import { createAction, props } from "@ngrx/store";
import { ListaCasacionesModel } from '../../../../../domain/models/Casacion.model';

export const cargarListaCasaciones = createAction(
    '[LayoutComponent] CARGAR LISTA CASACIONES',
    props<{ lista: ListaCasacionesModel|null }>()
);
