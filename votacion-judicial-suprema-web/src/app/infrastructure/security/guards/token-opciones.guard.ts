import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { LocalStorageService } from '../../services/local/local-storage.service';
import { catchError, map, of } from 'rxjs';
import { tokenNiveles } from '../../../domain/commons/constants';
import { AuthValidationService } from './auth-validation.service';

export const tokenOpcionesGuard: CanActivateFn = (route, state) => {
  const localStorageService = inject(LocalStorageService);
  const authValidationService = inject(AuthValidationService);
  return localStorageService.getTokenNivel().pipe(
    map(tokenNivel => {
      if(tokenNivel !==  tokenNiveles.NIVEL_OPCIONES){
        authValidationService.goLogin();
      }
      return tokenNivel ===  tokenNiveles.NIVEL_OPCIONES
    }), 
    catchError(() => of(false)) 
  );
};
