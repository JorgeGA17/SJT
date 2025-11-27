import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './infrastructure/shared/pages/page-not-found/page-not-found/page-not-found.component';

const routes: Routes = [
  { path: '', redirectTo: 'autenticacion', pathMatch: 'full' },
  { path: 'autenticacion', loadChildren: () => import('./infrastructure/modules/autenticacion/autenticacion.module').then(m => m.AutenticacionModule)},  
  { path: 'votacion', loadChildren: () => import('./infrastructure/modules/votacion/votacion.module').then(m => m.VotacionModule)},  
  { path: '**', component: PageNotFoundComponent, data: { title: 'Página no encontrada' }}    
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
