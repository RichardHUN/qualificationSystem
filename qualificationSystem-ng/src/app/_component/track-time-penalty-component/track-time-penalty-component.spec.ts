import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackTimePenaltyComponent } from './track-time-penalty-component';

describe('TrackTimePenaltyComponent', () => {
  let component: TrackTimePenaltyComponent;
  let fixture: ComponentFixture<TrackTimePenaltyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrackTimePenaltyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrackTimePenaltyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
