import {Component, OnInit} from '@angular/core';
import {RacingDriver} from '../../_model/racing-driver';
import {RacingDriverClient} from '../../_service/racing-driver-client';
import {ActivatedRoute, Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

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
        } else {
          this.client
            .get(Number(params.get('number')!))
            .subscribe(racingDriver => {
              this.racingDriver = racingDriver;
            })
        }
      })
  }

  protected create(): void {
    this.client
      .create(this.racingDriver)
      .subscribe({
        next: racingDriver => {
          this.racingDriver = racingDriver;
          alert("Racing Driver created successfully!");
          this.router.navigate(['racing-drivers', racingDriver.number])
        },
        error: error => {
          alert(JSON.stringify(error));
        }
      })
  }

  protected update(): void {
    this.client
      .update(this.racingDriver)
      .subscribe({
        next: racingDriver => {
          this.racingDriver = racingDriver;
          alert("Racing Driver edited successfully!");
        },
        error: error => {
          alert(JSON.stringify(error));
        }
      })
  }

}
