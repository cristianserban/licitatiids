import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LicitatiiTestModule } from '../../../test.module';
import { LicitatieUpdateComponent } from 'app/entities/licitatie/licitatie-update.component';
import { LicitatieService } from 'app/entities/licitatie/licitatie.service';
import { Licitatie } from 'app/shared/model/licitatie.model';

describe('Component Tests', () => {
  describe('Licitatie Management Update Component', () => {
    let comp: LicitatieUpdateComponent;
    let fixture: ComponentFixture<LicitatieUpdateComponent>;
    let service: LicitatieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [LicitatieUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LicitatieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LicitatieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LicitatieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Licitatie(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Licitatie();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
