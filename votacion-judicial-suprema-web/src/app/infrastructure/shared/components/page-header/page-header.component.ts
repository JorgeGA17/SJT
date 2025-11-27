import { CommonModule, Location } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-page-header',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule],
  templateUrl: './page-header.component.html',
  styleUrl: './page-header.component.scss'
})
export class PageHeaderComponent {
  @Input() btn: boolean = true;
  @Input() back: string = '/';
  @Input() tip: string = 'Volver';
  @Input() titulo: string = '';
  @Input() descripcion: string = '';
  @Input() desactivarTip: boolean = false;
  @Input() backHistory: boolean = false;

  constructor(
    private location: Location,
    private route: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  goBack(): void {
    if (this.backHistory) {
      this.location.back();
    } else {
      this.route.navigate([this.back], { relativeTo: this.activatedRoute });
    }
  }
}
