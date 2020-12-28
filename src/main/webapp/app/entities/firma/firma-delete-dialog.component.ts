import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFirma } from 'app/shared/model/firma.model';
import { FirmaService } from './firma.service';

@Component({
  templateUrl: './firma-delete-dialog.component.html',
})
export class FirmaDeleteDialogComponent {
  firma?: IFirma;

  constructor(protected firmaService: FirmaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.firmaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('firmaListModification');
      this.activeModal.close();
    });
  }
}
