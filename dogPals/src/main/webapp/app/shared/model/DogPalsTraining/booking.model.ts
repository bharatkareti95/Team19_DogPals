import { BookStatus } from 'app/shared/model/enumerations/book-status.model';

export interface IBooking {
  id?: number;
  price?: number;
  status?: BookStatus;
  trainingId?: number;
}

export class Booking implements IBooking {
  constructor(public id?: number, public price?: number, public status?: BookStatus, public trainingId?: number) {}
}
