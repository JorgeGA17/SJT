import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthValidationService } from './auth-validation.service';

export const authPerfilesGuard: CanActivateFn = (route, state) => {
  const authValidationService = inject(AuthValidationService);
  const tokenValidao = authValidationService.tokenJWTEsValido();
  if(!tokenValidao){
    authValidationService.goLogin();
  }
  return tokenValidao;
  // logica de validacion permisos por url
};
