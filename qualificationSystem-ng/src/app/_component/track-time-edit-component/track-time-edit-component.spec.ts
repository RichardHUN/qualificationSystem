import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackTimeEditComponent } from './track-time-edit-component';

describe('TrackTimeEditComponent', () => {
  let component: TrackTimeEditComponent;
  let fixture: ComponentFixture<TrackTimeEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrackTimeEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrackTimeEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
