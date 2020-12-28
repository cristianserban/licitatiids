import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LicitatiiTestModule } from '../../../test.module';
import { FirmaComponent } from 'app/entities/firma/firma.component';
import { FirmaService } from 'app/entities/firma/firma.service';
import { Firma } from 'app/shared/model/firma.model';

describe('Component Tests', () => {
  describe('Firma Management Component', () => {
    let comp: FirmaComponent;
    let fixture: ComponentFixture<FirmaComponent>;
    let service: FirmaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [FirmaComponent],
      })
        .overrideTemplate(FirmaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FirmaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FirmaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Firma(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.firmas && comp.firmas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
