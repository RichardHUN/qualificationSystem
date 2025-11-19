import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RacingTrackListComponent } from './racing-track-list-component';

describe('RacingTrackListComponent', () => {
  let component: RacingTrackListComponent;
  let fixture: ComponentFixture<RacingTrackListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RacingTrackListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RacingTrackListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
