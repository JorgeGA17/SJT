import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent} from '../components/login/login.component';
import { PageNotFoundComponent } from 'src/app/infrastructure/shared/pages/page-not-found/page-not-found/page-not-found.component';
import { SeleccionSalaComponent } from '../components/seleccion-sala/seleccion-sala.component';

const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent, data: { title: 'Login' }},
    { path: 'seleccion-sala', component: SeleccionSalaComponent, data: { title: 'Sala' }},
    { path: '**', component: PageNotFoundComponent, data: { title: 'Página no encontrada' }}
  ];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AutenticacionRoutingModule { }
