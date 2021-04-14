import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPopular, Popular } from 'app/shared/model/DogPalsTraining/popular.model';
import { PopularService } from './popular.service';
import { ITraining } from 'app/shared/model/DogPalsTraining/training.model';
import { TrainingService } from 'app/entities/DogPalsTraining/training/training.service';

@Component({
  selector: 'jhi-popular-update',
  templateUrl: './popular-update.component.html',
})
export class PopularUpdateComponent implements OnInit {
  isSaving = false;
  trainings: ITraining[] = [];

  editForm = this.fb.group({
    id: [],
    likeOrDislike: [null, [Validators.required]],
    userId: [null, [Validators.required]],
    trainingId: [],
  });

  constructor(
    protected popularService: PopularService,
    protected trainingService: TrainingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ popular }) => {
      this.updateForm(popular);

      this.trainingService.query().subscribe((res: HttpResponse<ITraining[]>) => (this.trainings = res.body || []));
    });
  }

  updateForm(popular: IPopular): void {
    this.editForm.patchValue({
      id: popular.id,
      likeOrDislike: popular.likeOrDislike,
      userId: popular.userId,
      trainingId: popular.trainingId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const popular = this.createFromForm();
    if (popular.id !== undefined) {
      this.subscribeToSaveResponse(this.popularService.update(popular));
    } else {
      this.subscribeToSaveResponse(this.popularService.create(popular));
    }
  }

  private createFromForm(): IPopular {
    return {
      ...new Popular(),
      id: this.editForm.get(['id'])!.value,
      likeOrDislike: this.editForm.get(['likeOrDislike'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      trainingId: this.editForm.get(['trainingId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPopular>>): void {
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
