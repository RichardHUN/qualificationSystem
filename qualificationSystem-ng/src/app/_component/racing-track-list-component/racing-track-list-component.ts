import {Component, OnInit} from '@angular/core';
import {RacingTrack} from '../../_model/racing-track';
import {RacingTrackClient} from '../../_service/racing-track-client';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-racing-track-list-component',
  imports: [
    RouterLink
  ],
  templateUrl: './racing-track-list-component.html',
  styleUrl: './racing-track-list-component.css',
})
export class RacingTrackListComponent implements OnInit {

  protected racingTracks!: RacingTrack[];

  constructor(
    private client: RacingTrackClient
  ) {
  }

  ngOnInit(): void {
    this.client
      .findAll()
      .subscribe({
        next: response => {
          this.racingTracks = response;
        }
      });
  }

  protected delete(city: string): void {
    this.client
      .delete(city)
      .subscribe(response => {
        this.ngOnInit();
      })
  }

}
