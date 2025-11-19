import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {RacingTrack} from '../_model/racing-track';
import {catchError, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RacingTrackClient {
  private readonly rootUrl: string =
    'http://localhost:8084/api/racing-tracks';

  constructor(
    private http: HttpClient
  ) {}

  public findAll() {
    return this.http
      .get<RacingTrack[]>(this.rootUrl);
  }

  delete(city: string): Observable<void> {
    return this.http
      .delete<void>(`${this.rootUrl}/${city}`);
  }

  get(city: string): Observable<RacingTrack> {
    return this.http
      .get<RacingTrack>(`${this.rootUrl}/${city}`);
  }

  create(racingTrack: RacingTrack): Observable<RacingTrack> {
    return this.http
      .post<RacingTrack>(this.rootUrl, racingTrack);
  }

  update(racingTrack: RacingTrack): Observable<RacingTrack> {
    return this.http
      .put<RacingTrack>(`${this.rootUrl}/${racingTrack.city}`, racingTrack)
  }

  existsByCity(city: string): Observable<boolean> {
    return this.http
      .get<RacingTrack | null>(`${this.rootUrl}/${city}`)
      .pipe(
        map((track) => track !== null && track !== undefined),
        catchError(() => of(false))
      );
  }

}
