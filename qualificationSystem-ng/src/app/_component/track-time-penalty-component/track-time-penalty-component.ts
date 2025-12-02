import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {TrackTimeClient} from '../../_service/track-time-client';
import {ActivatedRoute, Router} from '@angular/router';
import {TrackTime} from '../../_model/track-time';

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
    this.penalty = penalty;
  }

  protected addPenalty(): void {
    this.client
      .penalty(this.trackTime.id, this.penalty)
      .subscribe({
        next: () => {
          // open success modal, close after 3s and navigate; if user closes early navigate immediately
          this.openUpdateSuccessModal.nativeElement.click();
          let timeoutId: any;
          const closeEl = this.closeUpdateSuccessModal.nativeElement;
          const onCloseClick = () => {
            if (timeoutId) { clearTimeout(timeoutId); }
            closeEl.removeEventListener('click', onCloseClick);
            this.router.navigate(['/track-times']);
          };
          closeEl.addEventListener('click', onCloseClick);
          timeoutId = window.setTimeout(() => {
            closeEl.removeEventListener('click', onCloseClick);
            try { this.closeUpdateSuccessModal.nativeElement.click(); } catch (e) {}
            this.router.navigate(['/track-times']);
          }, 1500);
        },
        error: () => {
          this.openCreateErrorModal.nativeElement.click();
          const closeEl = this.closeCreateErrorModal.nativeElement;
          const onCloseClick = () => {
            closeEl.removeEventListener('click', onCloseClick);
          };
          closeEl.addEventListener('click', onCloseClick);
          window.setTimeout(() => {
            closeEl.removeEventListener('click', onCloseClick);
            try { this.closeCreateErrorModal.nativeElement.click(); } catch (e) {}
          }, 1500);
        }
      });
  }

}
