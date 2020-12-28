import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LicitatiiTestModule } from '../../../test.module';
import { GarantieUpdateComponent } from 'app/entities/garantie/garantie-update.component';
import { GarantieService } from 'app/entities/garantie/garantie.service';
import { Garantie } from 'app/shared/model/garantie.model';

describe('Component Tests', () => {
  describe('Garantie Management Update Component', () => {
    let comp: GarantieUpdateComponent;
    let fixture: ComponentFixture<GarantieUpdateComponent>;
    let service: GarantieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [GarantieUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GarantieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GarantieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GarantieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Garantie(123);
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
        const entity = new Garantie();
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
