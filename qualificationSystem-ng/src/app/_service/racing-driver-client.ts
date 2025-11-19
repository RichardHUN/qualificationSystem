import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RacingDriver} from '../_model/racing-driver';
import {Observable, of} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RacingDriverClient {
  private readonly rootUrl: string =
    'http://localhost:8084/api/racing-drivers';

  constructor(
    private http: HttpClient
  ) {}

  public findAll() {
    return this.http
      .get<RacingDriver[]>(this.rootUrl);
  }

  delete(number: number): Observable<void> {
    return this.http
      .delete<void>(`${this.rootUrl}/${number}`);
  }

  create(racingDriver: RacingDriver): Observable<RacingDriver> {
    return this.http
      .post<RacingDriver>(this.rootUrl, racingDriver);
  }

  update(racingDriver: RacingDriver): Observable<RacingDriver> {
    return this.http
      .put<RacingDriver>(this.rootUrl + '/' +racingDriver.number, racingDriver)
  }

  get(number: number): Observable<RacingDriver> {
    return this.http
      .get<RacingDriver>(`${this.rootUrl}/${number}`);
  }

  existsByNumber(number: number): Observable<boolean> {
    return this.http
      .get<RacingDriver | null>(`${this.rootUrl}/${number}`)
      .pipe(
        map((driver) => driver !== null && driver !== undefined),
        catchError(() => of(false))
      );
  }

}
