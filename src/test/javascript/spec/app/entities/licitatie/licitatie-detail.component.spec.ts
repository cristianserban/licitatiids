import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LicitatiiTestModule } from '../../../test.module';
import { LicitatieDetailComponent } from 'app/entities/licitatie/licitatie-detail.component';
import { Licitatie } from 'app/shared/model/licitatie.model';

describe('Component Tests', () => {
  describe('Licitatie Management Detail Component', () => {
    let comp: LicitatieDetailComponent;
    let fixture: ComponentFixture<LicitatieDetailComponent>;
    const route = ({ data: of({ licitatie: new Licitatie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [LicitatieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LicitatieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LicitatieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load licitatie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.licitatie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
