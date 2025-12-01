import {RacingDriver} from './racing-driver';
import {RacingTrack} from './racing-track';

export class TrackTime {
  id!: string;
  time!: string;
  recordedAt!: Date;
  driver!: RacingDriver;
  track!: RacingTrack;
}
