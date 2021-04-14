import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DogPalsTestModule } from '../../../../test.module';
import { RatingDetailComponent } from 'app/entities/DogPalsForum/rating/rating-detail.component';
import { Rating } from 'app/shared/model/DogPalsForum/rating.model';

describe('Component Tests', () => {
  describe('Rating Management Detail Component', () => {
    let comp: RatingDetailComponent;
    let fixture: ComponentFixture<RatingDetailComponent>;
    const route = ({ data: of({ rating: new Rating(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DogPalsTestModule],
        declarations: [RatingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RatingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RatingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rating on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rating).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
