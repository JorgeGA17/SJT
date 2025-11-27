import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatRippleModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-menu-bandeja',
  imports: [CommonModule, MatIconModule, MatButtonModule, MatRippleModule],
  templateUrl: './menu-bandeja.html',
  styleUrl: './menu-bandeja.scss'
})
export class MenuBandeja {
  constructor(private route: Router, private activatedRoute: ActivatedRoute){

  }
  irProyectos() :void {
    this.route.navigate(['/votaciones/bandeja/proyectar']);
  }

  irValidacion(): void {
    this.route.navigate(['/votaciones/bandeja/validar']);
  }

  back() :void {
    this.route.navigate(['/votaciones/menu']);
  }
}
