import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGarantie } from 'app/shared/model/garantie.model';
import { GarantieService } from './garantie.service';

@Component({
  templateUrl: './garantie-delete-dialog.component.html',
})
export class GarantieDeleteDialogComponent {
  garantie?: IGarantie;

  constructor(protected garantieService: GarantieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.garantieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('garantieListModification');
      this.activeModal.close();
    });
  }
}
