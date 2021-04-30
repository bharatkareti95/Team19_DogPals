import { Moment } from 'moment';
import { IComment } from 'app/shared/model/DogPalsForum/comment.model';
import { IRating } from 'app/shared/model/DogPalsForum/rating.model';

export interface IPost {
  id?: number;
  title?: string;
  content?: any;
  date?: Moment;
  comments?: IComment[];
  ratings?: IRating[];
  forumId?: number;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public title?: string,
    public content?: any,
    public date?: Moment,
    public comments?: IComment[],
    public ratings?: IRating[],
    public forumId?: number
  ) {}
}
