import { Routes } from '@angular/router';
import { tokenLoginGuard } from '../../../security/guards/token-login.guard';
import { MainLayout } from '../../../layouts/main-layout/main-layout';
import { Main } from '../components/main/main';
import { Votacion } from '../components/votacion/votacion';
import { Calendario } from '../components/votacion/calendario/calendario';
import { Registro } from '../components/votacion/registro/registro';
import { Bandeja } from '../components/bandeja/bandeja';
import { Proyectos } from '../components/bandeja/proyectos/proyectos';
import { Validar } from '../components/bandeja/validar/validar';
import { MenuBandeja } from '../components/bandeja/menu-bandeja/menu-bandeja';
import { importProvidersFrom } from '@angular/core';
import { StoreModule } from '@ngrx/store';
import { appVotacionesReducers } from '../store/votaciones.reducers';

export const VOTACIONES_ROUTES: Routes = [
    {
        path: '',
        component: MainLayout,
        providers: [
        importProvidersFrom(
            // register feature reducer
            StoreModule.forFeature('VotacionesModule', appVotacionesReducers)
            // run feature effects
            //EffectsModule.forFeature([MusiciansApiEffects])
        ),
        ],
        children: [
            { path: '', redirectTo: 'menu', pathMatch: 'full' },
            { path: 'menu', canActivate: [tokenLoginGuard], 
                component: Main, 
                data: { title: 'Menu' } 
            },
            { path: 'votacion', 
                canActivate: [tokenLoginGuard],
                component: Votacion, 
                data: { title: 'votacion' },
                children: [
                    { path: '', redirectTo: 'calendario', pathMatch: 'full' },
                    { path: 'calendario', canActivate: [tokenLoginGuard], 
                        component: Calendario},
                    { path: 'detalle', canActivate: [tokenLoginGuard], 
                        component: Registro},
                ]
            },
            { path: 'bandeja', 
                canActivate: [tokenLoginGuard],
                component: Bandeja, 
                data: { title: 'bandeja' },
                children: [
                    { path: '', redirectTo: 'menu-bandeja', pathMatch: 'full' },
                    { path: 'menu-bandeja', canActivate: [tokenLoginGuard], 
                        component: MenuBandeja},
                    { path: 'proyectar', canActivate: [tokenLoginGuard], 
                        component: Proyectos},
                    { path: 'validar', canActivate: [tokenLoginGuard], 
                        component: Validar},
                ]
            },
            { path: '**', loadComponent: () => import('./../../../shared/pages/page-not-found/page-not-found.component').then((m) => m.PageNotFoundComponent) },
        ]
    }
]