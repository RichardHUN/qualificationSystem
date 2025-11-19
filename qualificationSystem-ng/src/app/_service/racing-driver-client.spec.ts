import { TestBed } from '@angular/core/testing';

import { RacingDriverClient } from './racing-driver-client';

describe('RacingDriverClient', () => {
  let service: RacingDriverClient;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RacingDriverClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
