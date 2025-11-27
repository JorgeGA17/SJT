import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ErrorCredencialesInterceptor } from "./infrastructure/security/ErrorCredencialesInterceptor";
import { JwtInterceptor } from "./infrastructure/security/JwtInterceptor";
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';

import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { VjEffectsArray } from './infrastructure/global-store/vj.effects';
import { appVJReducers } from './infrastructure/global-store/vj.reducers';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from "./app.component";
import { RouterModule } from '@angular/router';
import { DatePipe, registerLocaleData } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

import { PageNotFoundModule } from "./infrastructure/shared/pages/page-not-found/page-not-found.module";
import { TranslateHttpLoader} from '@ngx-translate/http-loader';
import localeEs from '@angular/common/locales/es';

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient, './assets/i18n/', '.json');
}

registerLocaleData(localeEs);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    RouterModule,
    HttpClientModule,
    PageNotFoundModule,
    FormsModule,
    ReactiveFormsModule,
    StoreModule.forRoot( appVJReducers ),
    EffectsModule.forRoot( VjEffectsArray ),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    DatePipe,
    { provide: LOCALE_ID, useValue: 'es-ES' },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorCredencialesInterceptor, multi: true }    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
