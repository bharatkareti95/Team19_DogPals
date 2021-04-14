import { Moment } from 'moment';

export interface IInformation {
  id?: number;
  title?: string;
  content?: any;
  date?: Moment;
}

export class Information implements IInformation {
  constructor(public id?: number, public title?: string, public content?: any, public date?: Moment) {}
}
