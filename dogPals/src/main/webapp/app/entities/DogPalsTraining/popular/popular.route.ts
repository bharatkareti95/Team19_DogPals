import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPopular, Popular } from 'app/shared/model/DogPalsTraining/popular.model';
import { PopularService } from './popular.service';
import { PopularComponent } from './popular.component';
import { PopularDetailComponent } from './popular-detail.component';
import { PopularUpdateComponent } from './popular-update.component';

@Injectable({ providedIn: 'root' })
export class PopularResolve implements Resolve<IPopular> {
  constructor(private service: PopularService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPopular> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((popular: HttpResponse<Popular>) => {
          if (popular.body) {
            return of(popular.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Popular());
  }
}

export const popularRoute: Routes = [
  {
    path: '',
    component: PopularComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dogPalsApp.dogPalsTrainingPopular.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PopularDetailComponent,
    resolve: {
      popular: PopularResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dogPalsApp.dogPalsTrainingPopular.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PopularUpdateComponent,
    resolve: {
      popular: PopularResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dogPalsApp.dogPalsTrainingPopular.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PopularUpdateComponent,
    resolve: {
      popular: PopularResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dogPalsApp.dogPalsTrainingPopular.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
