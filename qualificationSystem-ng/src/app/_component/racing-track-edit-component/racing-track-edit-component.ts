import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RacingDriver} from '../../_model/racing-driver';
import {RacingDriverClient} from '../../_service/racing-driver-client';
import {ActivatedRoute, Router} from '@angular/router';
import {RacingTrack} from '../../_model/racing-track';
import {delay, tap} from 'rxjs';
import {RacingTrackClient} from '../../_service/racing-track-client';

@Component({
  selector: 'app-racing-track-edit-component',
  imports: [
    FormsModule
  ],
  templateUrl: './racing-track-edit-component.html',
  styleUrl: './racing-track-edit-component.css',
})
export class RacingTrackEditComponent implements OnInit {

  protected racingTrack!: RacingTrack;

  protected trackExists: boolean = false;

  @ViewChild('openUpdateSuccessModal')
  protected openUpdateSuccessModal!: ElementRef;

  @ViewChild('closeUpdateSuccessModal')
  protected closeUpdateSuccessModal!: ElementRef;

  @ViewChild('openCreateSuccessModal')
  protected openCreateSuccessModal!: ElementRef;

  @ViewChild('closeCreateSuccessModal')
  protected closeCreateSuccessModal!: ElementRef;

  @ViewChild('openCreateErrorModal')
  protected openCreateErrorModal!: ElementRef;

  @ViewChild('closeCreateErrorModal')
  protected closeCreateErrorModal!: ElementRef;

  constructor(
    private client: RacingTrackClient,
    private route: ActivatedRoute,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .subscribe(params => {
        if(params.get('city') == 'create'){
          this.racingTrack = {} as RacingTrack;
          this.trackExists = false;
        } else {
          const trackCity = params.get('city')!;
          this.client
            .get(trackCity)
            .subscribe(racingTrack => {
              this.racingTrack = racingTrack;
              this.checkTrackExists(trackCity);
            })
        }
      })
  }

  protected create(): void {
    this.client
      .create(this.racingTrack)
      .pipe(
        tap(response => this.racingTrack = response),
        tap(() => this.openCreateSuccessModal.nativeElement.click()),
        delay(5000),
        tap(() => this.closeCreateSuccessModal.nativeElement.click())
      )
      .subscribe({
        next: racingTrack => {
          this.router.navigate(['racing-tracks'])
        },
        error: () => {
          this.openCreateErrorModal.nativeElement.click();
          delay(5000);
          this.closeCreateErrorModal.nativeElement.click();
        }
      })
  }

  protected update(): void {
    this.client
      .update(this.racingTrack)
      .pipe(
        tap(response => this.racingTrack = response),
        tap(() => this.openUpdateSuccessModal.nativeElement.click()),
        delay(5000),
        tap(() => this.closeUpdateSuccessModal.nativeElement.click())
      )
      .subscribe();
  }

  protected checkTrackExists(city: string): void {
    if(!city) {
      this.trackExists = false;
      return;
    }
    this.client.existsByCity(city)
      .subscribe(exists => this.trackExists = exists);
  }

}
