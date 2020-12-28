import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOcol, Ocol } from 'app/shared/model/ocol.model';
import { OcolService } from './ocol.service';

@Component({
  selector: 'jhi-ocol-update',
  templateUrl: './ocol-update.component.html',
})
export class OcolUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numeOcol: [],
    ordine: [],
  });

  constructor(protected ocolService: OcolService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ocol }) => {
      this.updateForm(ocol);
    });
  }

  updateForm(ocol: IOcol): void {
    this.editForm.patchValue({
      id: ocol.id,
      numeOcol: ocol.numeOcol,
      ordine: ocol.ordine,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ocol = this.createFromForm();
    if (ocol.id !== undefined) {
      this.subscribeToSaveResponse(this.ocolService.update(ocol));
    } else {
      this.subscribeToSaveResponse(this.ocolService.create(ocol));
    }
  }

  private createFromForm(): IOcol {
    return {
      ...new Ocol(),
      id: this.editForm.get(['id'])!.value,
      numeOcol: this.editForm.get(['numeOcol'])!.value,
      ordine: this.editForm.get(['ordine'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOcol>>): void {
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
