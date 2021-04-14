import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPopular } from 'app/shared/model/DogPalsTraining/popular.model';
import { PopularService } from './popular.service';

@Component({
  templateUrl: './popular-delete-dialog.component.html',
})
export class PopularDeleteDialogComponent {
  popular?: IPopular;

  constructor(protected popularService: PopularService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.popularService.delete(id).subscribe(() => {
      this.eventManager.broadcast('popularListModification');
      this.activeModal.close();
    });
  }
}
