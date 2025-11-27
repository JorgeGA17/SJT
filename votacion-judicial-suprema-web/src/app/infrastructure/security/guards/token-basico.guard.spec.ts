import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { tokenBasicoGuard } from './token-basico.guard';

describe('tokenBasicoGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => tokenBasicoGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
