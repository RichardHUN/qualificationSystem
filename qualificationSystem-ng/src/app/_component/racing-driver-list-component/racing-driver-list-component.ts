import {Component, OnInit} from '@angular/core';
import {RacingDriver} from '../../_model/racing-driver';
import {RacingDriverClient} from '../../_service/racing-driver-client';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-racing-driver-list-component',
  imports: [
    RouterLink
  ],
  templateUrl: './racing-driver-list-component.html',
  styleUrl: './racing-driver-list-component.css',
})
export class RacingDriverListComponent
  implements OnInit {

  protected racingDrivers!: RacingDriver[];

  constructor(
    private client: RacingDriverClient,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.client
      .findAll()
      .subscribe({
        next: response => {
          this.racingDrivers = response;
        }
      });
  }

  protected delete(number: number): void {
    this.client
      .delete(number)
      .subscribe(response => {
        this.ngOnInit();
      })
  }

  protected update(number: number): void {
    this.router
      .navigate([`/racing-driver/${number}`]);
  }

}
