import { TestBed } from '@angular/core/testing';

import { JurisprudenciaService } from './jurisprudencia-service';

describe('JurisprudenciaService', () => {
  let service: JurisprudenciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JurisprudenciaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
