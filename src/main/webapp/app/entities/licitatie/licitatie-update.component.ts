import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILicitatie, Licitatie } from 'app/shared/model/licitatie.model';
import { LicitatieService } from './licitatie.service';
import { IFirma } from 'app/shared/model/firma.model';
import { FirmaService } from 'app/entities/firma/firma.service';
import { ILot } from 'app/shared/model/lot.model';
import { LotService } from 'app/entities/lot/lot.service';

type SelectableEntity = IFirma | ILot;

@Component({
  selector: 'jhi-licitatie-update',
  templateUrl: './licitatie-update.component.html',
})
export class LicitatieUpdateComponent implements OnInit {
  isSaving = false;
  firmas: IFirma[] = [];
  lots: ILot[] = [];
  dataLicitatieDp: any;

  editForm = this.fb.group({
    id: [],
    dataLicitatie: [],
    activa: [],
    tipLicitatie: [],
    firmas: [],
    lots: [],
  });

  constructor(
    protected licitatieService: LicitatieService,
    protected firmaService: FirmaService,
    protected lotService: LotService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licitatie }) => {
      this.updateForm(licitatie);

      this.firmaService.query().subscribe((res: HttpResponse<IFirma[]>) => (this.firmas = res.body || []));

      this.lotService.query().subscribe((res: HttpResponse<ILot[]>) => (this.lots = res.body || []));
    });
  }

  updateForm(licitatie: ILicitatie): void {
    this.editForm.patchValue({
      id: licitatie.id,
      dataLicitatie: licitatie.dataLicitatie,
      activa: licitatie.activa,
      tipLicitatie: licitatie.tipLicitatie,
      firmas: licitatie.firmas,
      lots: licitatie.lots,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const licitatie = this.createFromForm();
    if (licitatie.id !== undefined) {
      this.subscribeToSaveResponse(this.licitatieService.update(licitatie));
    } else {
      this.subscribeToSaveResponse(this.licitatieService.create(licitatie));
    }
  }

  private createFromForm(): ILicitatie {
    return {
      ...new Licitatie(),
      id: this.editForm.get(['id'])!.value,
      dataLicitatie: this.editForm.get(['dataLicitatie'])!.value,
      activa: this.editForm.get(['activa'])!.value,
      tipLicitatie: this.editForm.get(['tipLicitatie'])!.value,
      firmas: this.editForm.get(['firmas'])!.value,
      lots: this.editForm.get(['lots'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILicitatie>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
