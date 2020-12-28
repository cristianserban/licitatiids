import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILot, Lot } from 'app/shared/model/lot.model';
import { LotService } from './lot.service';
import { IOcol } from 'app/shared/model/ocol.model';
import { OcolService } from 'app/entities/ocol/ocol.service';

@Component({
  selector: 'jhi-lot-update',
  templateUrl: './lot-update.component.html',
})
export class LotUpdateComponent implements OnInit {
  isSaving = false;
  ocols: IOcol[] = [];

  editForm = this.fb.group({
    id: [],
    nrFisa: [],
    stare: [],
    pretPornire: [],
    volumBrut: [],
    volumNet: [],
    volumCoaja: [],
    ocol: [],
  });

  constructor(
    protected lotService: LotService,
    protected ocolService: OcolService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lot }) => {
      this.updateForm(lot);

      this.ocolService.query().subscribe((res: HttpResponse<IOcol[]>) => (this.ocols = res.body || []));
    });
  }

  updateForm(lot: ILot): void {
    this.editForm.patchValue({
      id: lot.id,
      nrFisa: lot.nrFisa,
      stare: lot.stare,
      pretPornire: lot.pretPornire,
      volumBrut: lot.volumBrut,
      volumNet: lot.volumNet,
      volumCoaja: lot.volumCoaja,
      ocol: lot.ocol,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lot = this.createFromForm();
    if (lot.id !== undefined) {
      this.subscribeToSaveResponse(this.lotService.update(lot));
    } else {
      this.subscribeToSaveResponse(this.lotService.create(lot));
    }
  }

  private createFromForm(): ILot {
    return {
      ...new Lot(),
      id: this.editForm.get(['id'])!.value,
      nrFisa: this.editForm.get(['nrFisa'])!.value,
      stare: this.editForm.get(['stare'])!.value,
      pretPornire: this.editForm.get(['pretPornire'])!.value,
      volumBrut: this.editForm.get(['volumBrut'])!.value,
      volumNet: this.editForm.get(['volumNet'])!.value,
      volumCoaja: this.editForm.get(['volumCoaja'])!.value,
      ocol: this.editForm.get(['ocol'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILot>>): void {
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

  trackById(index: number, item: IOcol): any {
    return item.id;
  }
}
