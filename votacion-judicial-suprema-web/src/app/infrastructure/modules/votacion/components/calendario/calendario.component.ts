import { Component, ElementRef, AfterViewInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ListaCasacionesModel } from 'src/app/domain/models/Casacion.model';


import { AppVJState } from '../../../../../infrastructure/global-store/vj.reducers';
import { Store } from '@ngrx/store';
import { GeneralService } from 'src/app/infrastructure/services/local/general/general.service';
import { Calendar } from 'primeng/calendar';
import { LoginService } from 'src/app/infrastructure/services/remote/autenticacion/login.service';

@Component({
  selector: 'app-calendario',
  templateUrl: './calendario.component.html',
  styleUrls: ['./calendario.component.scss']
})
export class CalendarioComponent implements AfterViewInit {

  //@ViewChild('targetComponent') targetComponent: ElementRef | undefined;
  @ViewChild('targetComponent') targetComponent!: Calendar;
  date :Date = new Date(); 
  encontroCasaciones: boolean = false;
  //fechaSel : string = '';
  //listaCas !: ListaCasacionesResponse;
  listaCasaciones : ListaCasacionesModel;

  salaUsuario: string = '';
  rutaImagen : string = '';

  constructor(private store: Store<AppVJState>,private router: Router, private generalService: GeneralService,private loginService: LoginService) {
    this.listaCasaciones = { lista : null, fecha: ''};

    this.salaUsuario = this.loginService.getSala();

    this.rutaImagen = "assets/img/main_vjs_" + this.salaUsuario + ".webp";
  }

  ngAfterViewInit(): void {
    this.generalService.clearCasacionSeleccionada();
    this.scrollToComponent();
  }

  scrollToComponent(): void {
    if (this.targetComponent && this.targetComponent.el) {
      const nativeElement = this.targetComponent.el.nativeElement;
      nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }

  seleccionarFecha(event:any): void {
    // this.fechaSel = this.datePipe.transform(this.date, 'yyyy-MM-dd') || '';
    if(this.date) {
      this.generalService.setFechaSeleccionada(this.date);
      this.router.navigate(['/votacion/registro']);
    }
    //this.listarCasaciones(this.fechaSel);
  }

  back(): void {
    this.router.navigate(['/votacion/main']);
  }

}
