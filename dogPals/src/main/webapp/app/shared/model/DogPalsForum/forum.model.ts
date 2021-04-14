import { IPost } from 'app/shared/model/DogPalsForum/post.model';
import { ListTopic } from 'app/shared/model/enumerations/list-topic.model';

export interface IForum {
  id?: number;
  topic?: ListTopic;
  posts?: IPost[];
}

export class Forum implements IForum {
  constructor(public id?: number, public topic?: ListTopic, public posts?: IPost[]) {}
}
