import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LicitatiiTestModule } from '../../../test.module';
import { FirmaDetailComponent } from 'app/entities/firma/firma-detail.component';
import { Firma } from 'app/shared/model/firma.model';

describe('Component Tests', () => {
  describe('Firma Management Detail Component', () => {
    let comp: FirmaDetailComponent;
    let fixture: ComponentFixture<FirmaDetailComponent>;
    const route = ({ data: of({ firma: new Firma(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [FirmaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FirmaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FirmaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load firma on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.firma).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
