import { Component, Input } from '@angular/core';
import { MenuItem } from '../../../../../domain/dto/local/menu-item';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'app-nav-submenu',
  standalone: true,
  imports: [CommonModule,
    RouterLink, 
    MatIconModule, 
    MatButtonModule, 
    MatMenuModule
  ],
  templateUrl: './nav-submenu.component.html',
  styleUrl: './nav-submenu.component.scss'
})
export class NavSubmenuComponent {
  @Input() menuOpciones?: MenuItem;
  @Input() primerNivel = false;
}
