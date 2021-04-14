import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IPopular } from 'app/shared/model/DogPalsTraining/popular.model';

type EntityResponseType = HttpResponse<IPopular>;
type EntityArrayResponseType = HttpResponse<IPopular[]>;

@Injectable({ providedIn: 'root' })
export class PopularService {
  public resourceUrl = SERVER_API_URL + 'services/dogpalstraining/api/populars';
  public resourceSearchUrl = SERVER_API_URL + 'services/dogpalstraining/api/_search/populars';

  constructor(protected http: HttpClient) {}

  create(popular: IPopular): Observable<EntityResponseType> {
    return this.http.post<IPopular>(this.resourceUrl, popular, { observe: 'response' });
  }

  update(popular: IPopular): Observable<EntityResponseType> {
    return this.http.put<IPopular>(this.resourceUrl, popular, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPopular>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPopular[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPopular[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
