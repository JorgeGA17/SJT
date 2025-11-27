import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatRippleModule } from '@angular/material/core';

@Component({
  selector: 'app-main',
  imports: [CommonModule, MatIconModule, MatButtonModule, MatRippleModule],
  templateUrl: './main.html',
  styleUrl: './main.scss'
})
export class Main {
  constructor(private route: Router){

  }
  goVotaciones(): void {
    this.route.navigate(['/votaciones/votacion']);
  }

  goBandejaT(): void {
    this.route.navigate(['/votaciones/bandeja']);
  }

  goReportes(): void {
    this.route.navigate(['/reportes']);
  }

  goSeleccionarSala(): void{
    this.route.navigate(['autenticacion/seleccion-sala']);
  }
}
