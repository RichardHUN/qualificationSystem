import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RacingDriver} from '../_model/racing-driver';

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

}
