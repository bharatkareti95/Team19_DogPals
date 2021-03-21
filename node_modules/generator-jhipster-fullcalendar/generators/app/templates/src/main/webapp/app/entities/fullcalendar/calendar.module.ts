import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

//<--! import -->
import { CalendarComponent } from './calendar.component';
import { CalendarDetailComponent } from './calendar-detail.component';
import { CalendarUpdateComponent } from './calendar-update.component';
import { CalendarDeleteDialogComponent } from './calendar-delete-dialog.component';
import { calendarRoute } from './calendar.route';

@NgModule({
  //<--! sharedmodule -->
  declarations: [CalendarComponent, CalendarDetailComponent, CalendarUpdateComponent, CalendarDeleteDialogComponent],
  entryComponents: [CalendarDeleteDialogComponent]
})
export class FullCalendarCalendarModule {}
