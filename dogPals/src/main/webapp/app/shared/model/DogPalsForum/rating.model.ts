import { LikeDisLike } from 'app/shared/model/enumerations/like-dis-like.model';

export interface IRating {
  id?: number;
  likeOrDislike?: LikeDisLike;
  relatedPostId?: number;
  postId?: number;
}

export class Rating implements IRating {
  constructor(public id?: number, public likeOrDislike?: LikeDisLike, public relatedPostId?: number, public postId?: number) {}
}
