import { BookStatus } from 'app/shared/model/enumerations/book-status.model';

export interface IBooking {
  id?: number;
  status?: BookStatus;
  userId?: number;
  trainingId?: number;
}

export class Booking implements IBooking {
  constructor(public id?: number, public status?: BookStatus, public userId?: number, public trainingId?: number) {}
}
