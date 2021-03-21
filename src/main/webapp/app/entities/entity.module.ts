import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'information',
        loadChildren: () => import('./information/information.module').then(m => m.DogpalsPresentationInformationModule),
      },
      {
        path: 'training',
        loadChildren: () => import('./dogpalsTraining/training/training.module').then(m => m.DogpalsTrainingTrainingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DogpalsPresentationEntityModule {}
