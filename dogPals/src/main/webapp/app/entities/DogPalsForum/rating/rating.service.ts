import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IRating } from 'app/shared/model/DogPalsForum/rating.model';

type EntityResponseType = HttpResponse<IRating>;
type EntityArrayResponseType = HttpResponse<IRating[]>;

@Injectable({ providedIn: 'root' })
export class RatingService {
  public resourceUrl = SERVER_API_URL + 'services/dogpalsforum/api/ratings';
  public resourceSearchUrl = SERVER_API_URL + 'services/dogpalsforum/api/_search/ratings';

  constructor(protected http: HttpClient) {}

  create(rating: IRating): Observable<EntityResponseType> {
    return this.http.post<IRating>(this.resourceUrl, rating, { observe: 'response' });
  }

  update(rating: IRating): Observable<EntityResponseType> {
    return this.http.put<IRating>(this.resourceUrl, rating, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRating>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRating[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRating[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
