import { createAction, props } from '@ngrx/store';
/* Accion para hacer login con datos de usuario para panel de administración */
export const mostrarCargando = createAction('[ComunComponent] MOSTRAR CARGANDO', props<{ estado: boolean }>());
