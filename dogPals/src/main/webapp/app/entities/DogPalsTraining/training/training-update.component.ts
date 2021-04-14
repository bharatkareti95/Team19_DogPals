import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITraining, Training } from 'app/shared/model/DogPalsTraining/training.model';
import { TrainingService } from './training.service';

@Component({
  selector: 'jhi-training-update',
  templateUrl: './training-update.component.html',
})
export class TrainingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.maxLength(50)]],
    date: [null, [Validators.required]],
    details: [null, [Validators.maxLength(2000)]],
    location: [null, [Validators.required]],
    price: [null, [Validators.required]],
    agency: [null, [Validators.required]],
    bookingStatus: [null, [Validators.required]],
    startTime: [null, [Validators.required]],
    endTime: [null, [Validators.required]],
    slot: [],
    popularity: [null, [Validators.required]],
  });

  constructor(protected trainingService: TrainingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => {
      if (!training.id) {
        const today = moment().startOf('day');
        training.date = today;
        training.startTime = today;
        training.endTime = today;
      }

      this.updateForm(training);
    });
  }

  updateForm(training: ITraining): void {
    this.editForm.patchValue({
      id: training.id,
      title: training.title,
      date: training.date ? training.date.format(DATE_TIME_FORMAT) : null,
      details: training.details,
      location: training.location,
      price: training.price,
      agency: training.agency,
      bookingStatus: training.bookingStatus,
      startTime: training.startTime ? training.startTime.format(DATE_TIME_FORMAT) : null,
      endTime: training.endTime ? training.endTime.format(DATE_TIME_FORMAT) : null,
      slot: training.slot,
      popularity: training.popularity,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const training = this.createFromForm();
    if (training.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingService.update(training));
    } else {
      this.subscribeToSaveResponse(this.trainingService.create(training));
    }
  }

  private createFromForm(): ITraining {
    return {
      ...new Training(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      details: this.editForm.get(['details'])!.value,
      location: this.editForm.get(['location'])!.value,
      price: this.editForm.get(['price'])!.value,
      agency: this.editForm.get(['agency'])!.value,
      bookingStatus: this.editForm.get(['bookingStatus'])!.value,
      startTime: this.editForm.get(['startTime'])!.value ? moment(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined,
      endTime: this.editForm.get(['endTime'])!.value ? moment(this.editForm.get(['endTime'])!.value, DATE_TIME_FORMAT) : undefined,
      slot: this.editForm.get(['slot'])!.value,
      popularity: this.editForm.get(['popularity'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraining>>): void {
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
}
