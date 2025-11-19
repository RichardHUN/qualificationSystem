import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RacingDriver} from '../_model/racing-driver';
import {Observable} from 'rxjs';

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

  get(number: number): Observable<RacingDriver> {
    return this.http
      .get<RacingDriver>(`${this.rootUrl}/${number}`);
  }

  create(racingDriver: RacingDriver): Observable<RacingDriver> {
    return this.http
      .post<RacingDriver>(this.rootUrl, racingDriver);
  }

  update(racingDriver: RacingDriver): Observable<RacingDriver> {
    return this.http
      .put<RacingDriver>(this.rootUrl + '/' +racingDriver.number, racingDriver)
  }

}
