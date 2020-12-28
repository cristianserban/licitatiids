import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGarantie } from 'app/shared/model/garantie.model';
import { GarantieService } from './garantie.service';
import { GarantieDeleteDialogComponent } from './garantie-delete-dialog.component';

@Component({
  selector: 'jhi-garantie',
  templateUrl: './garantie.component.html',
})
export class GarantieComponent implements OnInit, OnDestroy {
  garanties?: IGarantie[];
  eventSubscriber?: Subscription;

  constructor(protected garantieService: GarantieService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.garantieService.query().subscribe((res: HttpResponse<IGarantie[]>) => (this.garanties = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGaranties();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGarantie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGaranties(): void {
    this.eventSubscriber = this.eventManager.subscribe('garantieListModification', () => this.loadAll());
  }

  delete(garantie: IGarantie): void {
    const modalRef = this.modalService.open(GarantieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.garantie = garantie;
  }
}
