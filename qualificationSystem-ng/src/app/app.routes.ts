import { Routes } from '@angular/router';
import {RacingDriverListComponent} from './_component/racing-driver-list-component/racing-driver-list-component';

export const routes: Routes = [
  {path: 'racing-driver', component: RacingDriverListComponent},
  {path: '**', redirectTo: 'racing-driver'}
];
