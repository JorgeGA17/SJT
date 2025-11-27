import { ApplicationConfig, importProvidersFrom, LOCALE_ID, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { appVjReducers } from './infrastructure/global-store/vj.reducers';

import localeEsPe from '@angular/common/locales/es-PE';

import { JwtInterceptor } from './infrastructure/security/interceptors/JwtInterceptor';
import { ErrorCredencialesInterceptor } from './infrastructure/security/interceptors/ErrorCredencialesInterceptor';
import { auditoriaInterceptor } from './infrastructure/security/interceptors/AuditoriaInterceptor';
import { registerLocaleData } from '@angular/common';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';

registerLocaleData(localeEsPe);

export const MY_DATE_FORMATS = {
  parse: {
    dateInput: 'DD/MM/YYYY',
  },
  display: {
    dateInput: 'dd/MM/yyyy',
    monthYearLabel: 'MMMM yyyy',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM yyyy',
  },
};

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideStore(),
    provideEffects(),
    importProvidersFrom(
      StoreModule.forRoot( appVjReducers ),
      //StoreModule.forFeature('main', appVjReducers),
    ),
    provideHttpClient(
      withInterceptors([auditoriaInterceptor]),
      withInterceptorsFromDi()
    ),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorCredencialesInterceptor,
      multi: true,
    },
    { provide: LOCALE_ID, useValue: 'es-PE' }, // Configura el idioma para toda la app
    { provide: MAT_DATE_LOCALE, useValue: 'es-PE' }, // Configura el idioma del Datepicker
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }, // Configura el formato de fecha
]
};
