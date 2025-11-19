import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RacingDriverListComponent } from './racing-driver-list-component';

describe('RacingDriverListComponent', () => {
  let component: RacingDriverListComponent;
  let fixture: ComponentFixture<RacingDriverListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RacingDriverListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RacingDriverListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
