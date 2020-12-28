import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LicitatiiTestModule } from '../../../test.module';
import { OcolComponent } from 'app/entities/ocol/ocol.component';
import { OcolService } from 'app/entities/ocol/ocol.service';
import { Ocol } from 'app/shared/model/ocol.model';

describe('Component Tests', () => {
  describe('Ocol Management Component', () => {
    let comp: OcolComponent;
    let fixture: ComponentFixture<OcolComponent>;
    let service: OcolService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [OcolComponent],
      })
        .overrideTemplate(OcolComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OcolComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OcolService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ocol(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ocols && comp.ocols[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
