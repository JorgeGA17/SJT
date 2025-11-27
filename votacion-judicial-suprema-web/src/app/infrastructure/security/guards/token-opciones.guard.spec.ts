import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { tokenOpcionesGuard } from './token-opciones.guard';

describe('tokenOpcionesGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => tokenOpcionesGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
