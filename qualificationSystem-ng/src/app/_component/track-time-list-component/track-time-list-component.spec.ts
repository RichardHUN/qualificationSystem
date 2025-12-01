import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackTimeListComponent } from './track-time-list-component';

describe('TrackTimeListComponent', () => {
  let component: TrackTimeListComponent;
  let fixture: ComponentFixture<TrackTimeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrackTimeListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrackTimeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
