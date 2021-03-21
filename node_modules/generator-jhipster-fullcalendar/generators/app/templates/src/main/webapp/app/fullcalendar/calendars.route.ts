import { Route } from '@angular/router';
import { CalendarsComponent } from './calendars.component';

export const CALENDARS_ROUTE: Route = {
  path: 'fullcalendar',
  component: CalendarsComponent,
  data: {
    authorities: [],
    pageTitle: 'global.title'
  }
};
