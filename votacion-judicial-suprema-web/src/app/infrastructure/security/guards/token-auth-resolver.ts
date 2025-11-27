import { ResolveFn } from '@angular/router';
import { inject } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthUseCasesService } from '../../../use-cases/auth-use-cases.service';
import { tokenResponse } from '../../../domain/dto/remote/tokenResponse.dto';

export const tokenAuthResolver: ResolveFn<Observable<tokenResponse | null>> = (route, state) => {
  const authUseCasesService = inject(AuthUseCasesService);
  return authUseCasesService.getTokenAuth().pipe(
    catchError(() => of(null))
  );
};