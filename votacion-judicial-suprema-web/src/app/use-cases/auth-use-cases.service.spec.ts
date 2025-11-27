import { TestBed } from '@angular/core/testing';

import { AuthUseCasesService } from './auth-use-cases.service';

describe('AuthUseCasesService', () => {
  let service: AuthUseCasesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthUseCasesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
