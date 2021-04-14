import { LikeDisLike } from 'app/shared/model/enumerations/like-dis-like.model';

export interface IPopular {
  id?: number;
  likeOrDislike?: LikeDisLike;
  userId?: number;
  trainingId?: number;
}

export class Popular implements IPopular {
  constructor(public id?: number, public likeOrDislike?: LikeDisLike, public userId?: number, public trainingId?: number) {}
}
