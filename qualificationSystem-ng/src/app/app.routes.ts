import { Routes } from '@angular/router';
import {RacingDriverListComponent} from './_component/racing-driver-list-component/racing-driver-list-component';
import {RacingDriverEditComponent} from './_component/racing-driver-edit-component/racing-driver-edit-component';

export const routes: Routes = [
  {path: 'racing-driver', component: RacingDriverListComponent},
  {path: 'racing-driver/:number', component: RacingDriverEditComponent},
  {path: '**', redirectTo: 'racing-driver'}
];
