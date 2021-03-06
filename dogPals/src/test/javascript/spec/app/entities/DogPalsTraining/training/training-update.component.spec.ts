import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DogPalsTestModule } from '../../../../test.module';
import { TrainingUpdateComponent } from 'app/entities/DogPalsTraining/training/training-update.component';
import { TrainingService } from 'app/entities/DogPalsTraining/training/training.service';
import { Training } from 'app/shared/model/DogPalsTraining/training.model';

describe('Component Tests', () => {
  describe('Training Management Update Component', () => {
    let comp: TrainingUpdateComponent;
    let fixture: ComponentFixture<TrainingUpdateComponent>;
    let service: TrainingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DogPalsTestModule],
        declarations: [TrainingUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TrainingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Training(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Training();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
