import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DogPalsTestModule } from '../../../../test.module';
import { PopularDetailComponent } from 'app/entities/DogPalsTraining/popular/popular-detail.component';
import { Popular } from 'app/shared/model/DogPalsTraining/popular.model';

describe('Component Tests', () => {
  describe('Popular Management Detail Component', () => {
    let comp: PopularDetailComponent;
    let fixture: ComponentFixture<PopularDetailComponent>;
    const route = ({ data: of({ popular: new Popular(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DogPalsTestModule],
        declarations: [PopularDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PopularDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PopularDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load popular on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.popular).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
