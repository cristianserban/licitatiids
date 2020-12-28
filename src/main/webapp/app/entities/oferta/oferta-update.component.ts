import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOferta, Oferta } from 'app/shared/model/oferta.model';
import { OfertaService } from './oferta.service';
import { ILot } from 'app/shared/model/lot.model';
import { LotService } from 'app/entities/lot/lot.service';
import { IFirma } from 'app/shared/model/firma.model';
import { FirmaService } from 'app/entities/firma/firma.service';

type SelectableEntity = ILot | IFirma;

@Component({
  selector: 'jhi-oferta-update',
  templateUrl: './oferta-update.component.html',
})
export class OfertaUpdateComponent implements OnInit {
  isSaving = false;
  lots: ILot[] = [];
  firmas: IFirma[] = [];

  editForm = this.fb.group({
    id: [],
    castigatoare: [],
    pas: [],
    pret: [],
    lot: [],
    firma: [],
  });

  constructor(
    protected ofertaService: OfertaService,
    protected lotService: LotService,
    protected firmaService: FirmaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ oferta }) => {
      this.updateForm(oferta);

      this.lotService.query().subscribe((res: HttpResponse<ILot[]>) => (this.lots = res.body || []));

      this.firmaService.query().subscribe((res: HttpResponse<IFirma[]>) => (this.firmas = res.body || []));
    });
  }

  updateForm(oferta: IOferta): void {
    this.editForm.patchValue({
      id: oferta.id,
      castigatoare: oferta.castigatoare,
      pas: oferta.pas,
      pret: oferta.pret,
      lot: oferta.lot,
      firma: oferta.firma,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const oferta = this.createFromForm();
    if (oferta.id !== undefined) {
      this.subscribeToSaveResponse(this.ofertaService.update(oferta));
    } else {
      this.subscribeToSaveResponse(this.ofertaService.create(oferta));
    }
  }

  private createFromForm(): IOferta {
    return {
      ...new Oferta(),
      id: this.editForm.get(['id'])!.value,
      castigatoare: this.editForm.get(['castigatoare'])!.value,
      pas: this.editForm.get(['pas'])!.value,
      pret: this.editForm.get(['pret'])!.value,
      lot: this.editForm.get(['lot'])!.value,
      firma: this.editForm.get(['firma'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOferta>>): void {
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
}
