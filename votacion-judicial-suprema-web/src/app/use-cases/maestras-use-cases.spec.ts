import { TestBed } from '@angular/core/testing';

import { MaestrasUseCases } from './maestras-use-cases';

describe('MaestrasUseCases', () => {
  let service: MaestrasUseCases;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MaestrasUseCases);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
