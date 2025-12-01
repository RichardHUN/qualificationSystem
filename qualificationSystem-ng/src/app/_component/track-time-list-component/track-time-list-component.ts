import {Component, OnInit} from '@angular/core';
import {TrackTime} from '../../_model/track-time';
import {TrackTimeClient} from '../../_service/track-time-client';
import {DatePipe} from '@angular/common';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-track-time-list-component',
  imports: [
    DatePipe,
    RouterLink
  ],
  templateUrl: './track-time-list-component.html',
  styleUrl: './track-time-list-component.css',
})
export class TrackTimeListComponent implements OnInit {

  protected trackTimes!: TrackTime[];

  constructor(
    private client: TrackTimeClient,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.client
      .findAll()
      .subscribe({
        next: response => {
          this.trackTimes = response;
        }
      })
  }

  protected delete(id: string): void {
    this.client
      .delete(id)
      .subscribe(response => {
        this.ngOnInit();
      })
  }

  protected update(id: string): void {
    this.router
      .navigate([`/track-times/${id}`])
  }

  protected penalty(id: string) {
    this.router
      .navigate([`/track-times/${id}/penalty`])
  }
}
