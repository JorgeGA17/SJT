import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeleccionSalaComponent } from './seleccion-sala.component';

describe('SeleccionPerfilComponent', () => {
  let component: SeleccionSalaComponent;
  let fixture: ComponentFixture<SeleccionSalaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SeleccionSalaComponent]
    });
    fixture = TestBed.createComponent(SeleccionSalaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
