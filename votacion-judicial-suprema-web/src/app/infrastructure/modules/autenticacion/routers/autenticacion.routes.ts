import { Routes } from '@angular/router';
import { tokenAuthResolver } from '../../../security/guards/token-auth-resolver';
import { tokenLoginGuard } from '../../../security/guards/token-login.guard';
import { LoginLayout } from '../../../layouts/login-layout/login-layout';
import { Login } from '../components/login/login';
import { SeleccionSala } from '../components/seleccion-sala/seleccion-sala';

export const AUTENTICACION_ROUTES: Routes = [
  {
    path: '',
    component: LoginLayout,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', resolve: {tokenAuth: tokenAuthResolver},
        component: Login, 
        data: { title: 'Login' } },
      { path: 'seleccion-sala',  canActivate: [tokenLoginGuard],
        component: SeleccionSala, 
        data: { title: 'Sala' } },
      { path: '**', loadComponent: () => import('./../../../shared/pages/page-not-found/page-not-found.component').then((m) => m.PageNotFoundComponent) },
    ]
  }
];
