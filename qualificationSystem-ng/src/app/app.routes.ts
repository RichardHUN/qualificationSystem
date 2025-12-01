import { Routes } from '@angular/router';
import {RacingDriverListComponent} from './_component/racing-driver-list-component/racing-driver-list-component';
import {RacingDriverEditComponent} from './_component/racing-driver-edit-component/racing-driver-edit-component';
import {RacingTrackListComponent} from './_component/racing-track-list-component/racing-track-list-component';
import {RacingTrackEditComponent} from './_component/racing-track-edit-component/racing-track-edit-component';
import {TrackTimeListComponent} from './_component/track-time-list-component/track-time-list-component';
import {TrackTimeEditComponent} from './_component/track-time-edit-component/track-time-edit-component';
import {TrackTimePenaltyComponent} from './_component/track-time-penalty-component/track-time-penalty-component';

export const routes: Routes = [
  {path: 'racing-driver', component: RacingDriverListComponent},
  {path: 'racing-driver/:number', component: RacingDriverEditComponent},
  {path: 'racing-tracks', component: RacingTrackListComponent},
  {path: 'racing-tracks/:city', component: RacingTrackEditComponent},
  {path: 'track-times', component: TrackTimeListComponent},
  {path: 'track-times/:id', component: TrackTimeEditComponent},
  {path: 'track-times/:id/penalty', component: TrackTimePenaltyComponent},
  {path: '**', redirectTo: 'racing-driver'}
];
