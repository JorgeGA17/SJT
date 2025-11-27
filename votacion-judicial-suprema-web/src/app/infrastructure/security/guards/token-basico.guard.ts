import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { LocalStorageService } from '../../services/local/local-storage.service';
import { catchError, map, of } from 'rxjs';
import { tokenNiveles } from '../../../domain/commons/constants';

export const tokenBasicoGuard: CanActivateFn = (route, state) => {
  const localStorageService = inject(LocalStorageService);
  return localStorageService.getTokenNivel().pipe(
    map(tokenNivel => tokenNivel ===  tokenNiveles.NIVEL_AUTH), 
    catchError(() => of(false)) 
  );
};
