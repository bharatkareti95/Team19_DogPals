import { Moment } from 'moment';

export interface IComment {
  id?: number;
  title?: string;
  content?: any;
  date?: Moment;
  relatePostId?: number;
  postId?: number;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public title?: string,
    public content?: any,
    public date?: Moment,
    public relatePostId?: number,
    public postId?: number
  ) {}
}
