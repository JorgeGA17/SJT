import { TestBed } from '@angular/core/testing';

import { CasacionesService } from './casaciones-service';

describe('CasacionesService', () => {
  let service: CasacionesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CasacionesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
