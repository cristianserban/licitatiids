import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOcol } from 'app/shared/model/ocol.model';
import { OcolService } from './ocol.service';

@Component({
  templateUrl: './ocol-delete-dialog.component.html',
})
export class OcolDeleteDialogComponent {
  ocol?: IOcol;

  constructor(protected ocolService: OcolService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ocolService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ocolListModification');
      this.activeModal.close();
    });
  }
}
