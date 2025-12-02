import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RacingDriver} from '../../_model/racing-driver';
import {RacingDriverClient} from '../../_service/racing-driver-client';
import {ActivatedRoute, Router} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {tap} from 'rxjs';

@Component({
  selector: 'app-racing-driver-edit-component',
  imports: [
    FormsModule
  ],
  templateUrl: './racing-driver-edit-component.html',
  styleUrl: './racing-driver-edit-component.css',
})
export class RacingDriverEditComponent
  implements OnInit {

  protected racingDriver!: RacingDriver;

  protected driverExists: boolean = false;

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
    private client: RacingDriverClient,
    private route: ActivatedRoute,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .subscribe(params => {
        if(params.get('number') == 'create'){
          this.racingDriver = {} as RacingDriver;
          this.driverExists = false;
        } else {
          const driverNumber = Number(params.get('number')!);
          this.client
            .get(driverNumber)
            .subscribe(racingDriver => {
              this.racingDriver = racingDriver;
              this.checkDriverExists(driverNumber);
            })
        }
      })
  }

  protected create(): void {
    this.client
      .create(this.racingDriver)
      .pipe(
        tap(response => this.racingDriver = response),
        tap(() => this.openCreateSuccessModal.nativeElement.click())
      )
      .subscribe({
        next: () => {
          // ensure navigation happens after 3s even if user closes the modal early
          let timeoutId: any;
          const closeEl = this.closeCreateSuccessModal.nativeElement;
          const onCloseClick = () => {
            if (timeoutId) {
              clearTimeout(timeoutId);
            }
            closeEl.removeEventListener('click', onCloseClick);
            this.router.navigate(['racing-drivers']);
          };
          closeEl.addEventListener('click', onCloseClick);
          timeoutId = window.setTimeout(() => {
            closeEl.removeEventListener('click', onCloseClick);
            try { this.closeCreateSuccessModal.nativeElement.click(); } catch (e) {}
            this.router.navigate(['racing-drivers']);
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
      .update(this.racingDriver)
      .pipe(
        tap(response => this.racingDriver = response),
        tap(() => this.openUpdateSuccessModal.nativeElement.click())
      )
      .subscribe({
        next: () => {
          let timeoutId: any;
          const closeEl = this.closeUpdateSuccessModal.nativeElement;
          const onCloseClick = () => {
            if (timeoutId) {
              clearTimeout(timeoutId);
            }
            closeEl.removeEventListener('click', onCloseClick);
            this.router.navigate(['racing-drivers']);
          };
          closeEl.addEventListener('click', onCloseClick);
          timeoutId = window.setTimeout(() => {
            closeEl.removeEventListener('click', onCloseClick);
            try { this.closeUpdateSuccessModal.nativeElement.click(); } catch (e) {}
            this.router.navigate(['racing-drivers']);
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

  protected checkDriverExists(number: number): void {
    if(!number) {
      this.driverExists = false;
      return;
    }
    this.client.existsByNumber(number)
      .subscribe(exists => this.driverExists = exists);
  }

}
