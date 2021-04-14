import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IForum, Forum } from 'app/shared/model/DogPalsForum/forum.model';
import { ForumService } from './forum.service';

@Component({
  selector: 'jhi-forum-update',
  templateUrl: './forum-update.component.html',
})
export class ForumUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    topic: [null, [Validators.required]],
  });

  constructor(protected forumService: ForumService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ forum }) => {
      this.updateForm(forum);
    });
  }

  updateForm(forum: IForum): void {
    this.editForm.patchValue({
      id: forum.id,
      topic: forum.topic,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const forum = this.createFromForm();
    if (forum.id !== undefined) {
      this.subscribeToSaveResponse(this.forumService.update(forum));
    } else {
      this.subscribeToSaveResponse(this.forumService.create(forum));
    }
  }

  private createFromForm(): IForum {
    return {
      ...new Forum(),
      id: this.editForm.get(['id'])!.value,
      topic: this.editForm.get(['topic'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IForum>>): void {
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
