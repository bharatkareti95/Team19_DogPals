import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Booking } from 'app/shared/model/DogPalsTraining/booking.model';
import { BookingService } from './booking.service';
import { BookingDeleteDialogComponent } from './booking-delete-dialog.component';
import { User } from 'app/core/user/user.model';
import { AccountService } from 'app/core/auth/account.service';
import { UserService } from 'app/core/user/user.service';
import { Account } from 'app/core/user/account.model';
import { ITraining, Training } from 'app/shared/model/DogPalsTraining/training.model';
import { TrainingService } from '../training/training.service';
import { BookStatus } from 'app/shared/model/enumerations/book-status.model';
import { IUser } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-booking',
  templateUrl: './booking.component.html',
})
export class BookingComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  trainingStatus: Array<any> = [];
  noOfuser: Array<any> = [];
  user: IUser | null = null;
  bookings?: Booking[] | null = null;
  trainings?: ITraining[] | null = null;
  training?: Training | null = null;
  booking?: Booking | null = null;
  eventSubscriber?: Subscription;
  currentSearch: string;
  isUser?: User[];
  currentAccount: Account | null = null;
  private dateFormat = 'yyyy-MM-dd';

  constructor(

    private accountService: AccountService,
    protected trainingService: TrainingService,
    protected bookingService: BookingService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute,
    private userService: UserService,
    
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.isAuthority('ROLE_USER')){
      if ( this.isAuthority('ROLE_ADMIN')){
          this.loadAdminData();
      }else{
        this.bookingService.queryBookingForUserView({}).subscribe( (res: HttpResponse<Booking[]>) => {(this.bookings = res.body || []) , (this.beautifyDateForUser(this.bookings))});
      }
    }
    else {
      this.loadAdminData();
    }
    this.registerChangeInBookings();
   // this.trainingService.query({}).subscribe(( training => {this.trainings = training}));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.beforeLoadAll();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: Booking): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  trackTrainingId(index: number, item: ITraining): number {
    return item.id!;
  }

  registerChangeInBookings(): void {
    this.eventSubscriber = this.eventManager.subscribe('bookingListModification', () => this.loadAll());
  }

  delete(booking: Booking): void {
    const modalRef = this.modalService.open(BookingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.booking = booking;
  }

  isAuthority(authorities: string[] | string ): boolean {
    return this.accountService.hasAnyAuthority(authorities);
  }
  beforeLoadAll (): void{
    if (this.currentSearch) {
      this.bookingService
      .search({
        query: this.currentSearch,
      })
      .subscribe(

        (res: HttpResponse<Booking[]>) => (this.bookings = res.body || []),
        
        );
      return;
    }else{
      this.loadAll();
      }

  }

  loadAdminData () : void{
    this.trainingService.getTrainingsBookings().subscribe( (res:HttpResponse<ITraining[]>) => {(this.trainings = res.body || []), this.beautifyTrainingDataForUser(this.trainings)});
    this.accountService.identity().subscribe(account => {
      (this.account = account) ;
      this.userService.find(this.account?.login||'').subscribe(user => {
        this.user = user;
      })   
    
    })
  }

  private beautifyDateForUser(bookings : Booking[]): Booking[] {
    bookings.forEach(booking => {
      booking.status = BookStatus.Booked;
    });
    return bookings;
  }

  private beautifyTrainingDataForUser( trainings : ITraining []) : ITraining []{
    trainings.forEach(training => {
      const noOfBooking = training.bookings?.length;
      let  readyToRoll = false;

      training.bookings?.forEach(booking => {
        if ( (noOfBooking!=null && noOfBooking > 0 ) && booking?.status !== BookStatus.Booked){
          readyToRoll = true;
        }
        else {
          readyToRoll = false;
        }
      })
      if ( readyToRoll){this.trainingStatus?.push({key: training.id  , value:"Training is Confirmed"});
      }
      else { this.trainingStatus?.push({key: training.id  , value:"Training is awaiting Confirmation"});
      }
      if ( noOfBooking!=null && noOfBooking > 0 ){
        this.noOfuser?.push({key: training.id  , value: noOfBooking }) ;
      }
      else {
        this.noOfuser?.push({key: training.id  , value:"0"});
      }
    });
    return trainings;
  }
}
