import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGarantie, Garantie } from 'app/shared/model/garantie.model';
import { GarantieService } from './garantie.service';
import { IFirma } from 'app/shared/model/firma.model';
import { FirmaService } from 'app/entities/firma/firma.service';

@Component({
  selector: 'jhi-garantie-update',
  templateUrl: './garantie-update.component.html',
})
export class GarantieUpdateComponent implements OnInit {
  isSaving = false;
  firmas: IFirma[] = [];

  editForm = this.fb.group({
    id: [],
    garantie: [],
    tarif: [],
    garantieDepusa: [],
    firma: [],
  });

  constructor(
    protected garantieService: GarantieService,
    protected firmaService: FirmaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ garantie }) => {
      this.updateForm(garantie);

      this.firmaService.query().subscribe((res: HttpResponse<IFirma[]>) => (this.firmas = res.body || []));
    });
  }

  updateForm(garantie: IGarantie): void {
    this.editForm.patchValue({
      id: garantie.id,
      garantie: garantie.garantie,
      tarif: garantie.tarif,
      garantieDepusa: garantie.garantieDepusa,
      firma: garantie.firma,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const garantie = this.createFromForm();
    if (garantie.id !== undefined) {
      this.subscribeToSaveResponse(this.garantieService.update(garantie));
    } else {
      this.subscribeToSaveResponse(this.garantieService.create(garantie));
    }
  }

  private createFromForm(): IGarantie {
    return {
      ...new Garantie(),
      id: this.editForm.get(['id'])!.value,
      garantie: this.editForm.get(['garantie'])!.value,
      tarif: this.editForm.get(['tarif'])!.value,
      garantieDepusa: this.editForm.get(['garantieDepusa'])!.value,
      firma: this.editForm.get(['firma'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGarantie>>): void {
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

  trackById(index: number, item: IFirma): any {
    return item.id;
  }
}
