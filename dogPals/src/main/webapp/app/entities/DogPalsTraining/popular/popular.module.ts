import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DogPalsSharedModule } from 'app/shared/shared.module';
import { PopularComponent } from './popular.component';
import { PopularDetailComponent } from './popular-detail.component';
import { PopularUpdateComponent } from './popular-update.component';
import { PopularDeleteDialogComponent } from './popular-delete-dialog.component';
import { popularRoute } from './popular.route';

@NgModule({
  imports: [DogPalsSharedModule, RouterModule.forChild(popularRoute)],
  declarations: [PopularComponent, PopularDetailComponent, PopularUpdateComponent, PopularDeleteDialogComponent],
  entryComponents: [PopularDeleteDialogComponent],
})
export class DogPalsTrainingPopularModule {}
