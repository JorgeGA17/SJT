import { Routes } from '@angular/router';
import { tokenLoginGuard } from '../../../security/guards/token-login.guard';
import { MainLayout } from '../../../layouts/main-layout/main-layout';
import { ReporteMain } from '../components/reporte-main/reporte-main';


export const REPORTES_ROUTES: Routes = [
    {
        path: '',
        component: MainLayout,
        children: [
          { path: '', redirectTo: 'reporte-votaciones', pathMatch: 'full' },
          { path: 'reporte-votaciones', 
            canActivate: [tokenLoginGuard],
            component: ReporteMain, 
            data: { title: 'Login' } },
          { path: '**', loadComponent: () => import('./../../../shared/pages/page-not-found/page-not-found.component').then((m) => m.PageNotFoundComponent) },
        ]
    }
];