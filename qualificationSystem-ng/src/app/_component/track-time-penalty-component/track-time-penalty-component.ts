import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {TrackTimeClient} from '../../_service/track-time-client';
import {ActivatedRoute, Router} from '@angular/router';
import {TrackTime} from '../../_model/track-time';
import {delay} from 'rxjs';

@Component({
  selector: 'app-track-time-penalty-component',
  imports: [
    FormsModule
  ],
  templateUrl: './track-time-penalty-component.html',
  styleUrl: './track-time-penalty-component.css',
})
export class TrackTimePenaltyComponent implements OnInit {

  protected trackTime!: TrackTime;

  protected penalty!: number;

  @ViewChild('openUpdateSuccessModal')
  protected openUpdateSuccessModal!: ElementRef;

  @ViewChild('closeUpdateSuccessModal')
  protected closeUpdateSuccessModal!: ElementRef;

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

  ngOnInit(): void {
    this.route.paramMap
      .subscribe(params => {
        if(params.get('id')) {
          const trackTimeId = params.get('id')!;
          this.client
            .get(trackTimeId)
            .subscribe(trackTime => {
              this.trackTime = trackTime;
            })
        }
      })
  }

  protected updatePenalty(penalty: number): void {
    console.log(penalty);
    this.penalty = penalty;
  }

  protected addPenalty(): void {
    this.client
      .penalty(this.trackTime.id, this.penalty)
      .subscribe({
        next: trackTime => {
          this.router.navigate(['/track-times']);
        },
        error: () => {
          this.openCreateErrorModal.nativeElement.click();
          delay(5000);
          this.closeCreateErrorModal.nativeElement.click();
        }
      });
  }

}
