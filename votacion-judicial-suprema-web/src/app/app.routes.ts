import { Routes } from '@angular/router';
import { tokenOpcionesGuard } from './infrastructure/security/guards/token-opciones.guard';
import { tokenLoginGuard } from './infrastructure/security/guards/token-login.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'autenticacion', pathMatch: 'full' },
  {
    path: 'autenticacion',
    loadChildren: () => import('./infrastructure/modules/autenticacion/routers/autenticacion.routes').then((m) => m.AUTENTICACION_ROUTES),
  },
  {
    path: 'votaciones',
    canActivate: [tokenLoginGuard],
    loadChildren: () => import('./infrastructure/modules/votaciones/routers/votaciones.routes').then((m) => m.VOTACIONES_ROUTES),
  },
  {
    path: 'reportes',
    canActivate: [tokenLoginGuard],
    loadChildren: () => import('./infrastructure/modules/reportes/routers/reportes.routes').then((m) => m.REPORTES_ROUTES),
  },
  {
    path: '**',
    loadComponent: () => import('./infrastructure/shared/pages/page-not-found/page-not-found.component').then((m) => m.PageNotFoundComponent),
  },
];
