import { Moment } from 'moment';

export interface ITraining {
  id?: number;
  name?: string;
  description?: string;
  location?: string;
  date?: Moment;
}

export class Training implements ITraining {
  constructor(public id?: number, public name?: string, public description?: string, public location?: string, public date?: Moment) {}
}
