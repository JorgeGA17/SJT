import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteMain } from './reporte-main';

describe('ReporteMain', () => {
  let component: ReporteMain;
  let fixture: ComponentFixture<ReporteMain>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteMain]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteMain);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
