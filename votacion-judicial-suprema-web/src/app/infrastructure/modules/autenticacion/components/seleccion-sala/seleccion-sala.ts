import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUseCasesService } from '../../../../../use-cases/login-use-cases.service';
import { LocalStorageService } from '../../../../services/local/local-storage.service';
import { LocalStorageUsuarioService } from '../../../../services/local/local-storage-usuario.service';
import { AppVjState } from '../../../../global-store/vj.reducers';
import { Store } from '@ngrx/store';
import * as actions from '../../../../global-store/vj.actions';
import { constantes } from '../../../../../domain/commons/constants';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatRippleModule } from '@angular/material/core';
import { take } from 'rxjs';
import { Usuario, Instancia } from '../../../../../domain/dto/remote/LoginResponse.dto';

@Component({
  selector: 'app-seleccion-sala',
  imports: [CommonModule, MatIconModule, MatButtonModule, MatRippleModule],
  templateUrl: './seleccion-sala.html',
  styleUrl: './seleccion-sala.scss'
})
export class SeleccionSala implements OnInit {
 
  //listaPerfiles: any[] = [];
  instanciaSeleccionado : Instancia | null = null;
  usuario:Usuario | null = null;


  listaSalas: Instancia[] = [];
  /*listaSalas: any[] = [
    {id:"1",nombreInstancia:"2° SALA SUPREMA PENAL TRANSITORIA",codigoOrganoJurisdiccional:"una descripcion",select:false, icon:''},
    {id:"2",nombreInstancia:"2° SALA SUPREMA PENAL TRANSITORIA",codigoOrganoJurisdiccional:"una descripcion segundo",select:false, icon:''},
    {id:"3",nombreInstancia:"2° SALA SUPREMA PENAL TRANSITORIA",codigoOrganoJurisdiccional:"una descripcion tercero",select:false, icon:''}
  ];*/
  constructor(
    private route: Router,
    private loginUseCasesService: LoginUseCasesService,
    private localStorageService: LocalStorageService,
    private localStorageUsuarioService: LocalStorageUsuarioService,
    private store: Store<AppVjState>
  ) {
  }

  ngOnInit(): void {
   this.localStorageUsuarioService.getUsuario().pipe(take(1))
    .subscribe(usuario => {
      this.usuario = usuario;
      this.listaSalas = this.usuario? this.usuario?.instancias: [];
    });
  }

  selectedPerfil(indice: number, sala: Instancia): void {
    this.listaSalas.forEach(function (element, index, array) {
      element.select = false;
    });
    this.listaSalas[indice].select = true;
    this.instanciaSeleccionado = {...sala};
  }
  ingresar(){
    if(!this.instanciaSeleccionado){
      Swal.fire("Atención","seleccione una sala para continuar");
      return;
    }
    this.localStorageUsuarioService.setSala(this.instanciaSeleccionado);
    this.store.dispatch(actions.mostrarCargando({ estado: true}));
    this.route.navigate(['/votaciones/menu']);
  }
}

