import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILot } from 'app/shared/model/lot.model';
import { LotService } from './lot.service';
import { LotDeleteDialogComponent } from './lot-delete-dialog.component';

@Component({
  selector: 'jhi-lot',
  templateUrl: './lot.component.html',
})
export class LotComponent implements OnInit, OnDestroy {
  lots?: ILot[];
  eventSubscriber?: Subscription;

  constructor(protected lotService: LotService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.lotService.query().subscribe((res: HttpResponse<ILot[]>) => (this.lots = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLots();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILot): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLots(): void {
    this.eventSubscriber = this.eventManager.subscribe('lotListModification', () => this.loadAll());
  }

  delete(lot: ILot): void {
    const modalRef = this.modalService.open(LotDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lot = lot;
  }
}
