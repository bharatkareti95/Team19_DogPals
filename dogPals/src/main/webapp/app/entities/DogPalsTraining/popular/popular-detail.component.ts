import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPopular } from 'app/shared/model/DogPalsTraining/popular.model';

@Component({
  selector: 'jhi-popular-detail',
  templateUrl: './popular-detail.component.html',
})
export class PopularDetailComponent implements OnInit {
  popular: IPopular | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ popular }) => (this.popular = popular));
  }

  previousState(): void {
    window.history.back();
  }
}
