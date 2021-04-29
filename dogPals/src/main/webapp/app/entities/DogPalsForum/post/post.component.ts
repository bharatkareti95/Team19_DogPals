import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPost } from 'app/shared/model/DogPalsForum/post.model';
import { PostService } from './post.service';
import { PostDeleteDialogComponent } from './post-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { UserService } from 'app/core/user/user.service';
import { IUser } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-post',
  templateUrl: './post.component.html',
})
export class PostComponent implements OnInit, OnDestroy {
  posts?: IPost[];
  eventSubscriber?: Subscription;
  currentSearch: string;
  account: Account | null = null;
  user: IUser | null = null;
  constructor(
    protected postService: PostService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private userService: UserService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.postService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));
      return;
    }
    this.accountService.identity;

    if (this.isAuthority('ROLE_USER')) {
      if (this.isAuthority('ROLE_ADMIN')) {
        this.postService.query({}).subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));
      } else {
        this.postService.query({}).subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));
      }
    } else {
      this.postService.query().subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));
    }
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPosts();
    this.accountService.identity().subscribe(account => (this.account = account));
    this.userService.find(this.account?.login || '').subscribe(user => {
      this.user = user;
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPost): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPosts(): void {
    this.eventSubscriber = this.eventManager.subscribe('postListModification', () => this.loadAll());
  }

  delete(post: IPost): void {
    const modalRef = this.modalService.open(PostDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.post = post;
  }

  isAuthority(authorities: string[] | string): boolean {
    return this.accountService.hasAnyAuthority(authorities);
  }
}
