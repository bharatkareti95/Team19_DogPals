import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IForum } from 'app/shared/model/DogPalsForum/forum.model';

type EntityResponseType = HttpResponse<IForum>;
type EntityArrayResponseType = HttpResponse<IForum[]>;

@Injectable({ providedIn: 'root' })
export class ForumService {
  public resourceUrl = SERVER_API_URL + 'services/dogpalsforum/api/forums';
  public resourceSearchUrl = SERVER_API_URL + 'services/dogpalsforum/api/_search/forums';

  constructor(protected http: HttpClient) {}

  create(forum: IForum): Observable<EntityResponseType> {
    return this.http.post<IForum>(this.resourceUrl, forum, { observe: 'response' });
  }

  update(forum: IForum): Observable<EntityResponseType> {
    return this.http.put<IForum>(this.resourceUrl, forum, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IForum>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IForum[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IForum[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
