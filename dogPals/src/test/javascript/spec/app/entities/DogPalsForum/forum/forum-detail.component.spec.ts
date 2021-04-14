import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DogPalsTestModule } from '../../../../test.module';
import { ForumDetailComponent } from 'app/entities/DogPalsForum/forum/forum-detail.component';
import { Forum } from 'app/shared/model/DogPalsForum/forum.model';

describe('Component Tests', () => {
  describe('Forum Management Detail Component', () => {
    let comp: ForumDetailComponent;
    let fixture: ComponentFixture<ForumDetailComponent>;
    const route = ({ data: of({ forum: new Forum(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DogPalsTestModule],
        declarations: [ForumDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ForumDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ForumDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load forum on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.forum).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
