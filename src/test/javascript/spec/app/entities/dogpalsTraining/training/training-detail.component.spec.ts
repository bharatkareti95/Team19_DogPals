import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DogpalsPresentationTestModule } from '../../../../test.module';
import { TrainingDetailComponent } from 'app/entities/dogpalsTraining/training/training-detail.component';
import { Training } from 'app/shared/model/dogpalsTraining/training.model';

describe('Component Tests', () => {
  describe('Training Management Detail Component', () => {
    let comp: TrainingDetailComponent;
    let fixture: ComponentFixture<TrainingDetailComponent>;
    const route = ({ data: of({ training: new Training(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DogpalsPresentationTestModule],
        declarations: [TrainingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TrainingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load training on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.training).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
