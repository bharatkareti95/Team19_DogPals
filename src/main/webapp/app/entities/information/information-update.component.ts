import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInformation, Information } from 'app/shared/model/information.model';
import { InformationService } from './information.service';

@Component({
  selector: 'jhi-information-update',
  templateUrl: './information-update.component.html',
})
export class InformationUpdateComponent implements OnInit {
  isSaving = false;
  datepostedDp: any;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.maxLength(50)]],
    catagory: [null, [Validators.required]],
    dateposted: [null, [Validators.required]],
  });

  constructor(protected informationService: InformationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ information }) => {
      this.updateForm(information);
    });
  }

  updateForm(information: IInformation): void {
    this.editForm.patchValue({
      id: information.id,
      title: information.title,
      catagory: information.catagory,
      dateposted: information.dateposted,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const information = this.createFromForm();
    if (information.id !== undefined) {
      this.subscribeToSaveResponse(this.informationService.update(information));
    } else {
      this.subscribeToSaveResponse(this.informationService.create(information));
    }
  }

  private createFromForm(): IInformation {
    return {
      ...new Information(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      catagory: this.editForm.get(['catagory'])!.value,
      dateposted: this.editForm.get(['dateposted'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInformation>>): void {
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
