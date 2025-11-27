import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BandejaComponent } from './bandeja.component';

describe('BandejaComponent', () => {
  let component: BandejaComponent;
  let fixture: ComponentFixture<BandejaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BandejaComponent]
    });
    fixture = TestBed.createComponent(BandejaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
