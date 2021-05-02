import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBooking , Booking} from 'app/shared/model/DogPalsTraining/booking.model';

type EntityResponseType = HttpResponse<IBooking>;
type EntityArrayResponseType = HttpResponse<IBooking[]>;
type EntityArrayResponseTypeBooking = HttpResponse<Booking[]>;

@Injectable({ providedIn: 'root' })
export class BookingService {
  public resourceUrl = SERVER_API_URL + 'services/dogpalstraining/api/bookings';
  public resourceSearchUrl = SERVER_API_URL + 'services/dogpalstraining/api/_search/bookings';

  constructor(protected http: HttpClient) {}

  create(booking: IBooking): Observable<EntityResponseType> {
    return this.http.post<IBooking>(this.resourceUrl, booking, { observe: 'response' });
  }

  update(booking: IBooking): Observable<EntityResponseType> {
    return this.http.put<IBooking>(this.resourceUrl, booking, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBooking>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBooking[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  queryBookingForUserView(req?: any): Observable<EntityArrayResponseTypeBooking> {
    const options = createRequestOption(req);
    return this.http.get<Booking[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBooking[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
