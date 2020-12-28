import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LicitatiiTestModule } from '../../../test.module';
import { FirmaUpdateComponent } from 'app/entities/firma/firma-update.component';
import { FirmaService } from 'app/entities/firma/firma.service';
import { Firma } from 'app/shared/model/firma.model';

describe('Component Tests', () => {
  describe('Firma Management Update Component', () => {
    let comp: FirmaUpdateComponent;
    let fixture: ComponentFixture<FirmaUpdateComponent>;
    let service: FirmaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LicitatiiTestModule],
        declarations: [FirmaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FirmaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FirmaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FirmaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Firma(123);
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
        const entity = new Firma();
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
