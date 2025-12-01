import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {TrackTime} from '../../_model/track-time';
import {HttpClient} from '@angular/common/http';
import {TrackTimeClient} from '../../_service/track-time-client';
import {ActivatedRoute, Router} from '@angular/router';
import {JsonPipe} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {delay, tap} from 'rxjs';

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
        tap(() => this.openCreateSuccessModal.nativeElement.click()),
        delay(5000),
        tap(() => this.closeCreateSuccessModal.nativeElement.click())
      )
      .subscribe({
        next: trackTime =>{
          this.router.navigate(['track-times'])
        },
        error: err => {
          this.openCreateErrorModal.nativeElement.click();
          delay(5000);
          this.closeCreateErrorModal.nativeElement.click();
        }
      })
  }

  protected update(): void {
    this.client
      .update(this.trackTime)
      .pipe(
        tap(response => this.trackTime = response),
        tap(() => this.openUpdateSuccessModal.nativeElement.click()),
        delay(5000),
        tap(() => this.closeUpdateSuccessModal.nativeElement.click())
      )
      .subscribe();
  }

}
