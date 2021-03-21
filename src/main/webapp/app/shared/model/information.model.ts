import { Moment } from 'moment';

export interface IInformation {
  id?: number;
  title?: string;
  catagory?: string;
  dateposted?: Moment;
}

export class Information implements IInformation {
  constructor(public id?: number, public title?: string, public catagory?: string, public dateposted?: Moment) {}
}
