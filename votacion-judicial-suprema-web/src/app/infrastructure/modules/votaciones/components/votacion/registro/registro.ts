import { Component, OnInit, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { VotingNavigationComponent } from './voting-navigation/voting-navigation.component';
import { VotingContentComponent } from './voting-content/voting-content.component';
import Swal from 'sweetalert2';
import { AppVjState } from '../../../../../global-store/vj.reducers';
import { Store } from '@ngrx/store';
import * as actions from '../../../../../global-store/vj.actions';
import { AppVotacionesState } from '../../../store/votaciones.reducers';
import * as actionsVotaciones from '../../../store/actions';
import { LocalStorageUsuarioService } from '../../../../../services/local/local-storage-usuario.service';
import { LayoutService } from './layout.service';
import { CasacionDataModel } from '../../../../../../domain/models/Casacion.model';
import { Observable } from 'rxjs';
import * as querys from '../../../store/votaciones.selectors';



@Component({
  selector: 'app-registro',
  imports: [
    CommonModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    VotingNavigationComponent,
    VotingContentComponent
  ],
  templateUrl: './registro.html',
  styleUrl: './registro.scss'
})
export class Registro implements OnInit{
  // Inyección de dependencias
  detalleCasacion$!: Observable<CasacionDataModel | null>;

  constructor(private router: Router, private route:ActivatedRoute,
      private localStorageUsuarioService: LocalStorageUsuarioService,
      private store: Store<AppVjState>,
      private storeVotaciones: Store<AppVotacionesState>
  ){
    this.detalleCasacion$ = this.storeVotaciones.select(querys.getDetalleCasacion);
  }
  public layoutService = inject(LayoutService);

  selectedVotacion: CasacionDataModel| null =  null;
  sidenavOpen = this.layoutService.sidenavOpen;
  sidenavMode = this.layoutService.sidenavMode;
  isMobile = this.layoutService.isMobile;

  ngOnInit(): void {
    this.detalleCasacion$.subscribe((detalle: CasacionDataModel | null) => {
      if (detalle) {
        if (detalle) {
          this.selectedVotacion = { ...detalle };
        }
        else{
          //redirigir a calendario
        }
      }
    });



  }

  toggleSidenav(): void {
    this.layoutService.toggleSidenav();
  }

  onVotacionSelected(votacionId: string): void {
    // Actualizar la URL sin recargar el componente
    //this.router.navigate(['/voting', votacionId]);

    // En móvil, cerrar el sidenav al seleccionar
    if (this.isMobile()) {
      this.layoutService.closeSidenav();
    }
  }

  goToCalendar(): void {
    this.router.navigate(['/votaciones/votacion']);
  }
}
