import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/infrastructure/services/remote/autenticacion/login.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit{

  salaUsuario: string = '';
  rutaImagen : string = '';
  flagBandejaPermitida: boolean = true;
  dataUsuario: any;
  mostrarBtnRegresar = false;
  constructor(private router: Router,private loginService: LoginService) {
    
    this.salaUsuario = this.loginService.getSala();
    this.dataUsuario = this.loginService.getUsuario();
    this.rutaImagen = "assets/img/main_vjs_" + this.salaUsuario + ".webp";

    this.verificarBandejaPermitida();
  } 

  ngOnInit(): void {
    if(this.dataUsuario){
      this.mostrarBtnRegresar = this.dataUsuario.instancias.length > 1;
    }
  }
  verificarBandejaPermitida(){
    if(this.salaUsuario !== '209'){
      this.flagBandejaPermitida = false;
    }
  }

  goVotaciones(): void {
    this.router.navigate(['/votacion/listado']);
  }

  goBandejaT(): void {
    this.router.navigate(['/votacion/bandeja']);
  }

  goReportes(): void {
    this.router.navigate(['/votacion/reportes']);
  }

  goSeleccionarSala(): void{
    this.router.navigate(['autenticacion/seleccion-sala']);
  }

}
