import { TestBed } from '@angular/core/testing';

import { LoginUseCasesService } from './login-use-cases.service';

describe('LoginUseCasesService', () => {
  let service: LoginUseCasesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginUseCasesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
