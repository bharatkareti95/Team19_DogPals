import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPopular } from 'app/shared/model/DogPalsTraining/popular.model';
import { PopularService } from './popular.service';
import { PopularDeleteDialogComponent } from './popular-delete-dialog.component';

@Component({
  selector: 'jhi-popular',
  templateUrl: './popular.component.html',
})
export class PopularComponent implements OnInit, OnDestroy {
  populars?: IPopular[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected popularService: PopularService,
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
      this.popularService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IPopular[]>) => (this.populars = res.body || []));
      return;
    }

    this.popularService.query().subscribe((res: HttpResponse<IPopular[]>) => (this.populars = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPopulars();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPopular): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPopulars(): void {
    this.eventSubscriber = this.eventManager.subscribe('popularListModification', () => this.loadAll());
  }

  delete(popular: IPopular): void {
    const modalRef = this.modalService.open(PopularDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.popular = popular;
  }
}
