import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuBandeja } from './menu-bandeja';

describe('MenuBandeja', () => {
  let component: MenuBandeja;
  let fixture: ComponentFixture<MenuBandeja>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuBandeja]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuBandeja);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
