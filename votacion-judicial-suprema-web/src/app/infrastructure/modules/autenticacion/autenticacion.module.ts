import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AutenticacionRoutingModule } from "./routers/autenticacion-routing.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RecaptchaModule } from "ng-recaptcha";
//import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RippleModule } from 'primeng/ripple';
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { InputTextModule } from 'primeng/inputtext';
import { SelectButtonModule} from 'primeng/selectbutton';
import { ProgressBarModule } from 'primeng/progressbar';
import { BlockUIModule } from 'primeng/blockui';
import { DividerModule } from 'primeng/divider';
import { PanelModule } from 'primeng/panel';
import { PasswordModule } from 'primeng/password';
import { DropdownModule} from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';

import { NavbarModule } from '../../shared/components/navbar/navbar.module';
import { FooterModule } from '../../shared/components/footer/footer.module';
import { LoginComponent } from './components/login/login.component';
import { SeleccionSalaComponent } from './components/seleccion-sala/seleccion-sala.component';

// export function HttpLoaderFactory(http: HttpClient) {
//   return new TranslateHttpLoader(http, './assets/i18n/', '.json');
//   //return new TranslateHttpLoader(http);
// }

@NgModule({
  declarations: [
    LoginComponent,
    SeleccionSalaComponent
  ],
  imports: [
    CommonModule,
    AutenticacionRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    RecaptchaModule,
    FontAwesomeModule,
    InputTextModule,
    SelectButtonModule,
    ProgressBarModule,
    BlockUIModule,
    DividerModule,
    PanelModule,
    PasswordModule,
    DropdownModule,
    ButtonModule,
    RippleModule,
    // TranslateModule.forChild({
    //   loader: {
    //     provide: TranslateLoader,
    //     useFactory: HttpLoaderFactory,
    //     deps: [HttpClient]
    //   },
    //   isolate: true
    // }),
    NavbarModule,
    FooterModule
  ]
})
export class AutenticacionModule { }
