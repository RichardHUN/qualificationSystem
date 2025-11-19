import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RacingTrackEditComponent } from './racing-track-edit-component';

describe('RacingTrackEditComponent', () => {
  let component: RacingTrackEditComponent;
  let fixture: ComponentFixture<RacingTrackEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RacingTrackEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RacingTrackEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
