import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {TrackTime} from '../../_model/track-time';
import {TrackTimeClient} from '../../_service/track-time-client';
import {ActivatedRoute, Router} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {tap} from 'rxjs';

@Component({
  selector: 'app-track-time-edit-component',
  imports: [
    FormsModule
  ],
  templateUrl: './track-time-edit-component.html',
  styleUrl: './track-time-edit-component.css',
})
export class TrackTimeEditComponent implements OnInit {

  protected trackTime!: TrackTime;

  protected trackTimeExists: boolean = false;

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
    private client: TrackTimeClient,
    private route: ActivatedRoute,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(params => {
        if(params.get('id') == 'create'){
          this.trackTime = {
            driver: {},
            track: {}
          } as TrackTime;
          this.trackTimeExists = false;
        } else {
          const trackTimeId = params.get('id')!;
          this.client
            .get(trackTimeId)
            .subscribe(trackTime => {
              this.trackTime = trackTime;
              this.checkTrackTimeExists(trackTimeId);
            })
        }
      })
  }

  protected checkTrackTimeExists(id: string): void {
    if(!id){
      this.trackTimeExists = false;
      return;
    }
    this.client.exists(id)
      .subscribe(exists => this.trackTimeExists = exists);
  }

  protected create(): void {
    this.client
      .create(this.trackTime)
      .pipe(
        tap(response => this.trackTime = response),
        tap(() => this.openCreateSuccessModal.nativeElement.click())
      )
      .subscribe({
        next: () =>{
          let timeoutId: any;
          const closeEl = this.closeCreateSuccessModal.nativeElement;
          const onCloseClick = () => {
            if (timeoutId) { clearTimeout(timeoutId); }
            closeEl.removeEventListener('click', onCloseClick);
            this.router.navigate(['track-times']);
          };
          closeEl.addEventListener('click', onCloseClick);
          timeoutId = window.setTimeout(() => {
            closeEl.removeEventListener('click', onCloseClick);
            try { this.closeCreateSuccessModal.nativeElement.click(); } catch (e) {}
            this.router.navigate(['track-times']);
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
      .update(this.trackTime)
      .pipe(
        tap(response => this.trackTime = response),
        tap(() => this.openUpdateSuccessModal.nativeElement.click())
      )
      .subscribe({
        next: () =>{
          let timeoutId: any;
          const closeEl = this.closeUpdateSuccessModal.nativeElement;
          const onCloseClick = () => {
            if (timeoutId) { clearTimeout(timeoutId); }
            closeEl.removeEventListener('click', onCloseClick);
            this.router.navigate(['track-times']);
          };
          closeEl.addEventListener('click', onCloseClick);
          timeoutId = window.setTimeout(() => {
            closeEl.removeEventListener('click', onCloseClick);
            try { this.closeUpdateSuccessModal.nativeElement.click(); } catch (e) {}
            this.router.navigate(['track-times']);
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

}
