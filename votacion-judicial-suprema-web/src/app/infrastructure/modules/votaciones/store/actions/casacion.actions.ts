import { createAction, props } from '@ngrx/store';
import { CasacionDataModel } from '../../../../../domain/models/Casacion.model';

/* ************* Acciones para cargar detalle Casacion************** */
export const cargarDetalleCasacion = createAction('[LayoutComponent] CARGAR DETALLE CASACION', props<{ detalle: CasacionDataModel }>());

