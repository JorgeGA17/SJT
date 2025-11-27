import { Injectable } from '@angular/core';
import { constantes } from '../../../domain/commons/constants';
import { Instancia, Usuario } from '../../../domain/dto/remote/LoginResponse.dto';
import { MenuOpcionesSistema, OpcionSistema } from '../../../domain/dto/remote/OpcionesResponse.dto';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CryptoService } from '../../security/encryption/crypto.service';
import { map, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageUsuarioService {
  public readonly USUARIO = constantes.USUARIO;
  public readonly USUARIO_OPCIONES = constantes.USUARIO_OPCIONES;
  public readonly USUARIO_PERFIL = constantes.USUARIO_PERFIL;
  public readonly USUARIO_SALA = constantes.USUARIO_SALA;

  constructor(private route: Router, private cryptoService: CryptoService) {}

  /*setUsuario(usuario: Usuario) {
    this.cryptoService.encrypt(usuario).subscribe(encrypted => {
      localStorage.setItem(this.USUARIO, JSON.stringify(encrypted));
    });
  }*/
   setUsuario(usuario: Usuario) : Observable<void> {
    return this.cryptoService.encrypt(usuario).pipe(
      tap(encrypted => {
        localStorage.setItem(this.USUARIO, encrypted);
      }),
      map(() => void 0)
    );
  }

  setOpciones(opciones: MenuOpcionesSistema) {
    // opciones permitidas
    localStorage.setItem(this.USUARIO_OPCIONES, JSON.stringify(opciones));
  }

  setPerfil(perfiles: any) {
    // PERFIL
    localStorage.setItem(this.USUARIO_PERFIL, JSON.stringify(perfiles));
  }

  setSala(sala: Instancia) {
    // SALA
    localStorage.setItem(this.USUARIO_SALA, JSON.stringify(sala));
  }
  
  getUsuario() : Observable<Usuario | null> {
    const usuarioLocal = localStorage.getItem(this.USUARIO);
    if (!usuarioLocal) return of(null);
    return this.cryptoService.decrypt(usuarioLocal);
  }
  getOpciones() {
    let opcionesLocal: string | null = localStorage.getItem(this.USUARIO_OPCIONES);
    //console.log("opciones login services", opcionesLocal);
    if (opcionesLocal) {
      return JSON.parse(opcionesLocal);
    } else {
      return null;
    }
  }
  getPerfil() {
    let usuarioPerfil: string | null = localStorage.getItem(this.USUARIO_PERFIL);
    if (usuarioPerfil) {
      return JSON.parse(usuarioPerfil);
    } else {
      return null;
    }
  }
  getSala(): Observable<Instancia | null> {
    const salaString: string | null = localStorage.getItem(this.USUARIO_SALA);
    if (!salaString) return of(null);
    return of(JSON.parse(salaString));
  }

  removeUsuario() {
    localStorage.removeItem(this.USUARIO);
    localStorage.removeItem(this.USUARIO_OPCIONES);
    localStorage.removeItem(this.USUARIO_PERFIL);
  }

  removeOpcion() {
    localStorage.removeItem(this.USUARIO_OPCIONES);
  }

  removePerfil() {
    localStorage.removeItem(this.USUARIO_PERFIL);
  }

  clear() {
    localStorage.clear();
  }

  verificarPermisos(url: string) {
    this.getUsuario().subscribe(dataUsuario => {
      let dataPerfil: MenuOpcionesSistema = this.getOpciones();
      if (!dataUsuario) {
        Swal.fire('Atención!', 'Acceso denegado', 'info');
        this.route.navigate(['/autenticacion/login']);
      }
      if (!this.existeOpcion(url, dataPerfil.opciones)) {
        Swal.fire('Atención!', 'Acceso denegado a la opcion', 'info');
        this.route.navigate(['/autenticacion/login']);
      }
    });

  }

  existeOpcion(url: string, opciones: OpcionSistema[]): boolean {
    //console.log("opciones",opciones);
    for (let i = 0; i < opciones.length; i++) {
      if (opciones[i].url === url) {
        return true;
      }
    }
    return false;
  }

}
