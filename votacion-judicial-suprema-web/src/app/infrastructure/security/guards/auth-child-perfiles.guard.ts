import { CanActivateChildFn } from '@angular/router';
import { AuthValidationService } from './auth-validation.service';
import { inject } from '@angular/core';

export const authChildPerfilesGuard: CanActivateChildFn = (childRoute, state) => {
  const authValidationService = inject(AuthValidationService);
  const tokenValidao = authValidationService.tokenJWTEsValido();
  if(!tokenValidao){
    authValidationService.goLogin();
  }
  return tokenValidao;
};
