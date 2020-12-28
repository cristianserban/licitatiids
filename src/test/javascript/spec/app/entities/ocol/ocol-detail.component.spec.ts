import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LicitatiiTestModule } from '../../../test.module';
import { OcolDetailComponent } from 'app/entities/ocol/ocol-detail.component';
import { Ocol } from 'app/shared/model/ocol.model';

describe('Component Tests', () => {
  describe('Ocol Management Detail Component', () => {
    let comp: OcolDetailComponent;
    let fixture: ComponentFixture<OcolDetailComponent>;
    const route = ({ data: of({ ocol: new Ocol(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [OcolDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OcolDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OcolDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ocol on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ocol).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
