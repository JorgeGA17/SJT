import { TestBed } from '@angular/core/testing';

import { MaestrasService } from './maestras-service';

describe('MaestrasService', () => {
  let service: MaestrasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MaestrasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
