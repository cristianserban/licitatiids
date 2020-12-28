import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LicitatiiTestModule } from '../../../test.module';
import { OfertaComponent } from 'app/entities/oferta/oferta.component';
import { OfertaService } from 'app/entities/oferta/oferta.service';
import { Oferta } from 'app/shared/model/oferta.model';

describe('Component Tests', () => {
  describe('Oferta Management Component', () => {
    let comp: OfertaComponent;
    let fixture: ComponentFixture<OfertaComponent>;
    let service: OfertaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [OfertaComponent],
      })
        .overrideTemplate(OfertaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfertaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfertaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Oferta(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ofertas && comp.ofertas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
