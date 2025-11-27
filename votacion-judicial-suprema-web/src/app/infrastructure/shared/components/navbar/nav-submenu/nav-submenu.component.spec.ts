import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavSubmenuComponent } from './nav-submenu.component';

describe('NavSubmenuComponent', () => {
  let component: NavSubmenuComponent;
  let fixture: ComponentFixture<NavSubmenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavSubmenuComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavSubmenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
