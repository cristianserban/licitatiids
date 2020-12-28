import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFirma, Firma } from 'app/shared/model/firma.model';
import { FirmaService } from './firma.service';

@Component({
  selector: 'jhi-firma-update',
  templateUrl: './firma-update.component.html',
})
export class FirmaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numeFirma: [],
    dreptPreemtiune: [],
    volum: [],
  });

  constructor(protected firmaService: FirmaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ firma }) => {
      this.updateForm(firma);
    });
  }

  updateForm(firma: IFirma): void {
    this.editForm.patchValue({
      id: firma.id,
      numeFirma: firma.numeFirma,
      dreptPreemtiune: firma.dreptPreemtiune,
      volum: firma.volum,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const firma = this.createFromForm();
    if (firma.id !== undefined) {
      this.subscribeToSaveResponse(this.firmaService.update(firma));
    } else {
      this.subscribeToSaveResponse(this.firmaService.create(firma));
    }
  }

  private createFromForm(): IFirma {
    return {
      ...new Firma(),
      id: this.editForm.get(['id'])!.value,
      numeFirma: this.editForm.get(['numeFirma'])!.value,
      dreptPreemtiune: this.editForm.get(['dreptPreemtiune'])!.value,
      volum: this.editForm.get(['volum'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFirma>>): void {
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
}
