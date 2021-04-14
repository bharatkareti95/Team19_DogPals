import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DogPalsSharedModule } from 'app/shared/shared.module';
import { RatingComponent } from './rating.component';
import { RatingDetailComponent } from './rating-detail.component';
import { RatingUpdateComponent } from './rating-update.component';
import { RatingDeleteDialogComponent } from './rating-delete-dialog.component';
import { ratingRoute } from './rating.route';

@NgModule({
  imports: [DogPalsSharedModule, RouterModule.forChild(ratingRoute)],
  declarations: [RatingComponent, RatingDetailComponent, RatingUpdateComponent, RatingDeleteDialogComponent],
  entryComponents: [RatingDeleteDialogComponent],
})
export class DogPalsForumRatingModule {}
