import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import Swal from 'sweetalert2';
import * as actions from '../../../../../infrastructure/global-store/vj.actions';
import { constantes } from 'src/app/constants';
import { LoginService } from 'src/app/infrastructure/services/remote/autenticacion/login.service';
import { ProyectosService } from 'src/app/infrastructure/services/remote/casacion/proyectos.service';
import { AppVJState } from 'src/app/infrastructure/global-store/vj.reducers';

@Component({
  selector: 'app-bandeja',
  templateUrl: './bandeja.component.html',
  styleUrls: ['./bandeja.component.scss']
})
export class BandejaComponent {

  usuarioWeb : string = '';
  cantidadPendiente : number = 0;
  canditadPorValidar : number = 0;

  private distritoUsuario: string = '';
  private provinciaUsuario: string = '';
  private salaUsuario: string = '';
  nombreSalaUsuario: string = '';

  rutaImagen: string = '';

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private loginService: LoginService, private proyectosService: ProyectosService
    ,private store: Store<AppVJState>){
      
      this.distritoUsuario = this.loginService.getDistrito();
      this.provinciaUsuario = this.loginService.getProvincia();
      this.salaUsuario = this.loginService.getSala();
      this.nombreSalaUsuario = this.loginService.getNombreSala();

      this.rutaImagen = "assets/img/bandeja_trabajo_" + this.salaUsuario + ".webp"

      this.contarProyectos('1',null,null);
  }

  irProyectos() :void {
    this.router.navigate(['/votacion/proyectos']);
  }

  irValidacion(): void {
    if(this.canditadPorValidar > 0) {
      this.router.navigate(['/votacion/validar']);
    } else {
      Swal.fire('Información!', 'No tiene proyectos pendientes por validar.', 'info');
    }
  }

  contarProyectos(codigoEst:string|null, fInicio: string|null, fFin: string|null): void {
    this.usuarioWeb = this.loginService.getUsuario().codigoUsuario;
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.proyectosService.listarProyectos(this.distritoUsuario,this.provinciaUsuario,this.salaUsuario,this.usuarioWeb,codigoEst,fInicio,fFin).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.cantidadPendiente = response.data.length;
          } else{
            this.cantidadPendiente = 0;
          }
        }
        else{
          Swal.fire('¡Atención!', response.descripcion, 'warning');
        }
      },
      complete:()=>{
        this.contarPorValidar('2',null,null);
      },      
      error:(err)=>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    });      
}

contarPorValidar(codigoEst:string|null, fInicio: string|null, fFin: string|null): void {
  this.usuarioWeb = this.loginService.getUsuario().codigoUsuario;
  this.store.dispatch(actions.mostrarCargando({ estado: true }));
  this.proyectosService.listarProyectosPorValidar(this.distritoUsuario,this.provinciaUsuario,this.salaUsuario,this.usuarioWeb,codigoEst,fInicio,fFin).subscribe({
    next:(response:any)=>{
      if(response.codigo===constantes.RESPONSE_COD_EXITO){
        if(response.data){
          this.canditadPorValidar = response.data.length;
        } else{
          this.canditadPorValidar = 0;
        }
      }
      else{
        Swal.fire('¡Atención!', response.descripcion, 'warning');
      }
    },
    complete:()=>{
      this.store.dispatch(actions.mostrarCargando({ estado: false }));
    },      
    error:(err)=>{
      this.store.dispatch(actions.mostrarCargando({ estado: false }));
      Swal.fire('¡Atención!', err, 'warning');
    }
  });      
}


  back() :void {
    this.router.navigate(['../'], { relativeTo: this.activatedRoute });
  }
}
