import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardOfficer } from './dashboard-officer';

describe('DashboardOfficer', () => {
  let component: DashboardOfficer;
  let fixture: ComponentFixture<DashboardOfficer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardOfficer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardOfficer);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
