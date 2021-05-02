import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';

import { ITraining, Training } from 'app/shared/model/DogPalsTraining/training.model';
import { BookingService } from 'app/entities/DogPalsTraining/booking/booking.service';

import { Observable } from 'rxjs';

import { IBooking, Booking } from 'app/shared/model/DogPalsTraining/booking.model';
import { BookStatus } from 'app/shared/model/enumerations/book-status.model';
import { TrainingService } from './training.service';


@Component({
  selector: 'jhi-training-detail',
  templateUrl: './training-detail.component.html',
})
export class TrainingDetailComponent implements OnInit {
  training: ITraining | null = null;
  isSaving = false;

  constructor(protected activatedRoute: ActivatedRoute, protected bookingService: BookingService, protected trainingService : TrainingService ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => (this.training = training));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    // alert('Entering the save function now');
    this.isSaving = true;
    const booking = this.createFromForm();
    if ( this.checkCapacity() ){
      alert("No Training Capacity");
      this.onSaveError
    }
    else{
      this.subscribeToSaveResponse(this.bookingService.create(booking));
    }
  }

  private createFromForm(): IBooking {
    return {
      ...new Booking(),
     // id: this.editForm.get(['id'])!.value,
      status: BookStatus.Booked,
     // userId: this.editForm.get(['userId'])!.value,
     // adding randomValue
      userId: 0,
      trainingId: this.training!.id
    
    }
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBooking>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    if ( this.training !== undefined ){
      const updateTraining =  this.createForTrainingUpdate(); ;
      if ( updateTraining != null ) this.subscribeToSaveTrainingResponse(this.trainingService.update(updateTraining));
    }
   
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  checkCapacity(): boolean{
    return this.training?.capacity === 0
  }

  createForTrainingUpdate(): ITraining {
    if (this.training?.capacity?.valueOf() != null  &&  this.training?.capacity?.valueOf() !== 0 )
    return{
      ...new Training(),
      id: this.training?.id,
      title: this.training?.title,
      date: this.training?.date,
      details: this.training?.details,
      location: this.training?.location,
      price: this.training?.price,
      agency: this.training?.agency,
      startTime: this.training?.startTime,
      endTime: this.training?.endTime,
     
      capacity: this.training?.capacity?.valueOf()-1  ,
      popularity: this.training?.popularity,
      userId: 0,
    }
    else {
      return {
        ...new Training(),
      id: this.training?.id,
      title: this.training?.title,
      date: this.training?.date,
      details: this.training?.details,
      location: this.training?.location,
      price: this.training?.price,
      agency: this.training?.agency,
      startTime: this.training?.startTime,
      endTime: this.training?.endTime,
     
      capacity: this.training?.capacity,
      popularity: this.training?.popularity,
      userId: 0,
      }
    }
  }

  protected subscribeToSaveTrainingResponse(result: Observable<HttpResponse<ITraining>>): void {
    result.subscribe(
      () => this.onSaveTrainingSuccess(),
      () => this.onSaveTrainingError()
    );
  }

  protected onSaveTrainingSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveTrainingError(): void {
    this.isSaving = false;
  }

}
