import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRating } from 'app/shared/model/DogPalsForum/rating.model';
import { RatingService } from './rating.service';
import { RatingDeleteDialogComponent } from './rating-delete-dialog.component';

@Component({
  selector: 'jhi-rating',
  templateUrl: './rating.component.html',
})
export class RatingComponent implements OnInit, OnDestroy {
  ratings?: IRating[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected ratingService: RatingService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.ratingService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IRating[]>) => (this.ratings = res.body || []));
      return;
    }

    this.ratingService.query().subscribe((res: HttpResponse<IRating[]>) => (this.ratings = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRatings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRating): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRatings(): void {
    this.eventSubscriber = this.eventManager.subscribe('ratingListModification', () => this.loadAll());
  }

  delete(rating: IRating): void {
    const modalRef = this.modalService.open(RatingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rating = rating;
  }
}
