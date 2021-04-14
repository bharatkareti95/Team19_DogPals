import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBooking, Booking } from 'app/shared/model/DogPalsTraining/booking.model';
import { BookingService } from './booking.service';
import { ITraining } from 'app/shared/model/DogPalsTraining/training.model';
import { TrainingService } from 'app/entities/DogPalsTraining/training/training.service';

@Component({
  selector: 'jhi-booking-update',
  templateUrl: './booking-update.component.html',
})
export class BookingUpdateComponent implements OnInit {
  isSaving = false;
  trainings: ITraining[] = [];

  editForm = this.fb.group({
    id: [],
    price: [null, [Validators.required]],
    status: [null, [Validators.required]],
    trainingId: [],
  });

  constructor(
    protected bookingService: BookingService,
    protected trainingService: TrainingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ booking }) => {
      this.updateForm(booking);

      this.trainingService.query().subscribe((res: HttpResponse<ITraining[]>) => (this.trainings = res.body || []));
    });
  }

  updateForm(booking: IBooking): void {
    this.editForm.patchValue({
      id: booking.id,
      price: booking.price,
      status: booking.status,
      trainingId: booking.trainingId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const booking = this.createFromForm();
    if (booking.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingService.update(booking));
    } else {
      this.subscribeToSaveResponse(this.bookingService.create(booking));
    }
  }

  private createFromForm(): IBooking {
    return {
      ...new Booking(),
      id: this.editForm.get(['id'])!.value,
      price: this.editForm.get(['price'])!.value,
      status: this.editForm.get(['status'])!.value,
      trainingId: this.editForm.get(['trainingId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBooking>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITraining): any {
    return item.id;
  }
}
