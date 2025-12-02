import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {RacingTrack} from '../../_model/racing-track';
import {tap} from 'rxjs';
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
        tap(() => this.openCreateSuccessModal.nativeElement.click())
      )
      .subscribe({
        next: () => {
          let timeoutId: any;
          const closeEl = this.closeCreateSuccessModal.nativeElement;
          const onCloseClick = () => {
            if (timeoutId) { clearTimeout(timeoutId); }
            closeEl.removeEventListener('click', onCloseClick);
            this.router.navigate(['racing-tracks']);
          };
          closeEl.addEventListener('click', onCloseClick);
          timeoutId = window.setTimeout(() => {
            closeEl.removeEventListener('click', onCloseClick);
            try { this.closeCreateSuccessModal.nativeElement.click(); } catch (e) {}
            this.router.navigate(['racing-tracks']);
          }, 1500);
        },
        error: () => {
          this.openCreateErrorModal.nativeElement.click();
          window.setTimeout(() => {
            try { this.closeCreateErrorModal.nativeElement.click(); } catch (e) {}
          }, 1500);
        }
      })
  }

  protected update(): void {
    this.client
      .update(this.racingTrack)
      .pipe(
        tap(response => this.racingTrack = response),
        tap(() => this.openUpdateSuccessModal.nativeElement.click())
      )
      .subscribe({
        next: () => {
          let timeoutId: any;
          const closeEl = this.closeUpdateSuccessModal.nativeElement;
          const onCloseClick = () => {
            if (timeoutId) { clearTimeout(timeoutId); }
            closeEl.removeEventListener('click', onCloseClick);
            this.router.navigate(['racing-tracks']);
          };
          closeEl.addEventListener('click', onCloseClick);
          timeoutId = window.setTimeout(() => {
            closeEl.removeEventListener('click', onCloseClick);
            try { this.closeUpdateSuccessModal.nativeElement.click(); } catch (e) {}
            this.router.navigate(['racing-tracks']);
          }, 1500);
        },
        error: () => {
          this.openCreateErrorModal.nativeElement.click();
          window.setTimeout(() => {
            try { this.closeCreateErrorModal.nativeElement.click(); } catch (e) {}
          }, 1500);
        }
      });
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
