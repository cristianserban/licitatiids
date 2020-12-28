import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LicitatiiTestModule } from '../../../test.module';
import { GarantieComponent } from 'app/entities/garantie/garantie.component';
import { GarantieService } from 'app/entities/garantie/garantie.service';
import { Garantie } from 'app/shared/model/garantie.model';

describe('Component Tests', () => {
  describe('Garantie Management Component', () => {
    let comp: GarantieComponent;
    let fixture: ComponentFixture<GarantieComponent>;
    let service: GarantieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [GarantieComponent],
      })
        .overrideTemplate(GarantieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GarantieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GarantieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Garantie(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.garanties && comp.garanties[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
