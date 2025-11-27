import { TestBed } from '@angular/core/testing';

import { VistasCausaService } from './vistas-causa-service';

describe('VistasCausaService', () => {
  let service: VistasCausaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VistasCausaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
