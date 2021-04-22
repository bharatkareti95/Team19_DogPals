import { Moment } from 'moment';
import { IPopular } from 'app/shared/model/DogPalsTraining/popular.model';
import { IBooking } from 'app/shared/model/DogPalsTraining/booking.model';

export interface ITraining {
  id?: number;
  title?: string;
  date?: Moment;
  details?: string;
  location?: string;
  price?: number;
  agency?: string;
  startTime?: Moment;
  endTime?: Moment;
  capacity?: number;
  popularity?: number;
  populars?: IPopular[];
  bookings?: IBooking[];
}

export class Training implements ITraining {
  constructor(
    public id?: number,
    public title?: string,
    public date?: Moment,
    public details?: string,
    public location?: string,
    public price?: number,
    public agency?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public capacity?: number,
    public popularity?: number,
    public populars?: IPopular[],
    public bookings?: IBooking[]
  ) {}
}
