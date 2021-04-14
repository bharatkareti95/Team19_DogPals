import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DogPalsTestModule } from '../../../../test.module';
import { PopularUpdateComponent } from 'app/entities/DogPalsTraining/popular/popular-update.component';
import { PopularService } from 'app/entities/DogPalsTraining/popular/popular.service';
import { Popular } from 'app/shared/model/DogPalsTraining/popular.model';

describe('Component Tests', () => {
  describe('Popular Management Update Component', () => {
    let comp: PopularUpdateComponent;
    let fixture: ComponentFixture<PopularUpdateComponent>;
    let service: PopularService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DogPalsTestModule],
        declarations: [PopularUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PopularUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PopularUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PopularService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Popular(123);
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
        const entity = new Popular();
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
