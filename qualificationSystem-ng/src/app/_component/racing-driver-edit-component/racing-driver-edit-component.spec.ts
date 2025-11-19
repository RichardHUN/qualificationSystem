import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RacingDriverEditComponent } from './racing-driver-edit-component';

describe('RacingDriverEditComponent', () => {
  let component: RacingDriverEditComponent;
  let fixture: ComponentFixture<RacingDriverEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RacingDriverEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RacingDriverEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
