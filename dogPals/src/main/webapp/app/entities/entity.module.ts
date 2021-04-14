import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'training',
        loadChildren: () => import('./DogPalsTraining/training/training.module').then(m => m.DogPalsTrainingTrainingModule),
      },
      {
        path: 'booking',
        loadChildren: () => import('./DogPalsTraining/booking/booking.module').then(m => m.DogPalsTrainingBookingModule),
      },
      {
        path: 'popular',
        loadChildren: () => import('./DogPalsTraining/popular/popular.module').then(m => m.DogPalsTrainingPopularModule),
      },
      {
        path: 'information',
        loadChildren: () => import('./information/information.module').then(m => m.DogPalsInformationModule),
      },
      {
        path: 'forum',
        loadChildren: () => import('./DogPalsForum/forum/forum.module').then(m => m.DogPalsForumForumModule),
      },
      {
        path: 'post',
        loadChildren: () => import('./DogPalsForum/post/post.module').then(m => m.DogPalsForumPostModule),
      },
      {
        path: 'comment',
        loadChildren: () => import('./DogPalsForum/comment/comment.module').then(m => m.DogPalsForumCommentModule),
      },
      {
        path: 'rating',
        loadChildren: () => import('./DogPalsForum/rating/rating.module').then(m => m.DogPalsForumRatingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DogPalsEntityModule {}
