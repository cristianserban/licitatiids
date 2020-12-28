import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILicitatie } from 'app/shared/model/licitatie.model';
import { LicitatieService } from './licitatie.service';

@Component({
  templateUrl: './licitatie-delete-dialog.component.html',
})
export class LicitatieDeleteDialogComponent {
  licitatie?: ILicitatie;

  constructor(protected licitatieService: LicitatieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.licitatieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('licitatieListModification');
      this.activeModal.close();
    });
  }
}
