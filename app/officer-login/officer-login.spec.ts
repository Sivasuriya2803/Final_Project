import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficerLogin } from './officer-login';

describe('OfficerLogin', () => {
  let component: OfficerLogin;
  let fixture: ComponentFixture<OfficerLogin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OfficerLogin]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfficerLogin);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
