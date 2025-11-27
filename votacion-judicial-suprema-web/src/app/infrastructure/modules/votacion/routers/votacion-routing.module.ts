import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from '../components/main/main.component';
import { CalendarioComponent } from '../components/calendario/calendario.component';
import { PageNotFoundComponent } from 'src/app/infrastructure/shared/pages/page-not-found/page-not-found/page-not-found.component';
import { RegistroComponent } from '../components/registro/registro.component';
import { BandejaComponent } from '../components/bandeja/bandeja.component';
import { ProyectoComponent } from '../components/proyecto/proyecto.component';
import { ValidarComponent } from '../components/validar/validar.component';
import { ReportesComponent } from '../components/reportes/reportes.component';


const routes: Routes = [
    { path: '', redirectTo: 'main', pathMatch: 'full' },
    { path: 'main', component: MainComponent, data: { title: 'Inicio' } },
    { path: 'listado', component: CalendarioComponent, data: { title: 'Seleccionar fecha' } },
    { path: 'registro', component: RegistroComponent, data: { title: 'Votación' } },
    { path: 'bandeja', component: BandejaComponent, data: { title: 'Bandeja de trabajo' } },
    { path: 'proyectos', component: ProyectoComponent, data: { title: 'Proyectos pendientes' } },
    { path: 'validar', component: ValidarComponent, data: { title: 'Proyectos por validar' } },
    { path: 'reportes', component: ReportesComponent, data: { title: 'Reportes' } },
    { path: '**', component: PageNotFoundComponent, data: { title: 'Página no encontrada' } }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class VotacionRoutingModule { }
