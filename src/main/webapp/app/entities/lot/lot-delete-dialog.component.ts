import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILot } from 'app/shared/model/lot.model';
import { LotService } from './lot.service';

@Component({
  templateUrl: './lot-delete-dialog.component.html',
})
export class LotDeleteDialogComponent {
  lot?: ILot;

  constructor(protected lotService: LotService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lotService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lotListModification');
      this.activeModal.close();
    });
  }
}
