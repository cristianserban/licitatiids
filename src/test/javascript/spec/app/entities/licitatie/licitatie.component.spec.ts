import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LicitatiiTestModule } from '../../../test.module';
import { LicitatieComponent } from 'app/entities/licitatie/licitatie.component';
import { LicitatieService } from 'app/entities/licitatie/licitatie.service';
import { Licitatie } from 'app/shared/model/licitatie.model';

describe('Component Tests', () => {
  describe('Licitatie Management Component', () => {
    let comp: LicitatieComponent;
    let fixture: ComponentFixture<LicitatieComponent>;
    let service: LicitatieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [LicitatieComponent],
      })
        .overrideTemplate(LicitatieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LicitatieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LicitatieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Licitatie(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.licitaties && comp.licitaties[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
