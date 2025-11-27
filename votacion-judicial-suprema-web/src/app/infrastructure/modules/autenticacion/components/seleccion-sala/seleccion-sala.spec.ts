import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeleccionSala } from './seleccion-sala';

describe('SeleccionSala', () => {
  let component: SeleccionSala;
  let fixture: ComponentFixture<SeleccionSala>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SeleccionSala]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SeleccionSala);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
