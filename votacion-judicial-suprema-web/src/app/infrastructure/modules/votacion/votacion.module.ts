import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VotacionRoutingModule } from './routers/votacion-routing.module';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RecaptchaModule } from "ng-recaptcha";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { InputTextModule } from 'primeng/inputtext';
import { SelectButtonModule } from 'primeng/selectbutton';
import { PanelModule } from 'primeng/panel';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table'
import { InputTextareaModule } from 'primeng/inputtextarea';
import { RadioButtonModule } from 'primeng/radiobutton';
import { PickListModule } from 'primeng/picklist';
import { DialogModule } from 'primeng/dialog';
import { MultiSelectModule } from 'primeng/multiselect';
import { ChipsModule } from 'primeng/chips';
import { CheckboxModule } from 'primeng/checkbox';
import { BlockUIModule } from 'primeng/blockui';
import { TooltipModule } from 'primeng/tooltip';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { EditorModule } from 'primeng/editor';

import { NavbarModule } from '../../shared/components/navbar/navbar.module';
import { FooterModule } from '../../shared/components/footer/footer.module';
import { CalendarModule } from 'primeng/calendar';
import { MainComponent } from './components/main/main.component';
import { CalendarioComponent } from './components/calendario/calendario.component';
import { RegistroComponent } from './components/registro/registro.component';
import { BandejaComponent } from './components/bandeja/bandeja.component';
import { ProyectoComponent } from './components/proyecto/proyecto.component';
import { ValidarComponent } from './components/validar/validar.component';
import { ReportesComponent } from './components/reportes/reportes.component';

@NgModule({
    declarations: [
        MainComponent,
        CalendarioComponent,
        RegistroComponent,
        BandejaComponent,
        ProyectoComponent,
        ValidarComponent,
        ReportesComponent
    ],
    imports: [
        CommonModule,
        VotacionRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        RecaptchaModule,
        FontAwesomeModule,
        InputTextModule,
        SelectButtonModule,
        PanelModule,
        DropdownModule,
        ButtonModule,
        TableModule,
        InputTextareaModule,
        RadioButtonModule,
        PickListModule,
        DialogModule,
        MultiSelectModule,
        ChipsModule,
        CheckboxModule,
        BlockUIModule,
        TooltipModule,
        OverlayPanelModule,
        NavbarModule,
        FooterModule,
        CalendarModule,
        EditorModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VotacionModule { }
