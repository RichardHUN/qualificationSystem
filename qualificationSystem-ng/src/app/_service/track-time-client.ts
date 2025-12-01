import {HttpClient} from '@angular/common/http';
import {TrackTime} from '../_model/track-time';
import {Injectable} from '@angular/core';
import {catchError, map} from 'rxjs/operators';
import {of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TrackTimeClient {
  private readonly rootUrl: string =
    'api/track-times';

  constructor(
    private http: HttpClient,
  ) {
  }

  findAll() {
    return this.http
      .get<TrackTime[]>(this.rootUrl)
  }

  delete(id: string) {
    return this.http
      .delete<void>(`${this.rootUrl}/${id}`);
  }

  get(id: string) {
    return this.http
      .get<TrackTime>(`${this.rootUrl}/${id}`);
  }

  exists(id: string) {
    return this.http.get<TrackTime>(`${this.rootUrl}/${id}`).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

  create(trackTime: TrackTime) {
    return this.http
      .post<TrackTime>(this.rootUrl, trackTime);
  }

  update(trackTime: TrackTime) {
    return this.http
      .put<TrackTime>(`${this.rootUrl}/${trackTime.id}`, trackTime);
  }

  penalty(id: string, penalty: number){
    return this.http
      .put<TrackTime>(`${this.rootUrl}/${id}/penalty`, penalty);
  }

}
