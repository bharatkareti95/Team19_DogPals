import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

//<--! import -->
import { FullCalendarModule } from '@fullcalendar/angular';

import { CALENDARS_ROUTE } from './calendars.route';
import { CalendarsComponent } from './calendars.component';
import { EventModalComponent } from './event-modal.component'

@NgModule({
  //<--! sharedmodule -->
  declarations: [CalendarsComponent, EventModalComponent],
  entryComponents: [EventModalComponent]
})
export class CalendarsModule {
  
}