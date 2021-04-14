import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DogPalsTestModule } from '../../../../test.module';
import { PopularComponent } from 'app/entities/DogPalsTraining/popular/popular.component';
import { PopularService } from 'app/entities/DogPalsTraining/popular/popular.service';
import { Popular } from 'app/shared/model/DogPalsTraining/popular.model';

describe('Component Tests', () => {
  describe('Popular Management Component', () => {
    let comp: PopularComponent;
    let fixture: ComponentFixture<PopularComponent>;
    let service: PopularService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DogPalsTestModule],
        declarations: [PopularComponent],
      })
        .overrideTemplate(PopularComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PopularComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PopularService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Popular(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.populars && comp.populars[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
