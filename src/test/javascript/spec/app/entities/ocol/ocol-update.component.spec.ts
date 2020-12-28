import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LicitatiiTestModule } from '../../../test.module';
import { OcolUpdateComponent } from 'app/entities/ocol/ocol-update.component';
import { OcolService } from 'app/entities/ocol/ocol.service';
import { Ocol } from 'app/shared/model/ocol.model';

describe('Component Tests', () => {
  describe('Ocol Management Update Component', () => {
    let comp: OcolUpdateComponent;
    let fixture: ComponentFixture<OcolUpdateComponent>;
    let service: OcolService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [OcolUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OcolUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OcolUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OcolService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ocol(123);
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
        const entity = new Ocol();
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
