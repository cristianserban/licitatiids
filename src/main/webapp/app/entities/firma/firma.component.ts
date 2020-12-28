import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFirma } from 'app/shared/model/firma.model';
import { FirmaService } from './firma.service';
import { FirmaDeleteDialogComponent } from './firma-delete-dialog.component';

@Component({
  selector: 'jhi-firma',
  templateUrl: './firma.component.html',
})
export class FirmaComponent implements OnInit, OnDestroy {
  firmas?: IFirma[];
  eventSubscriber?: Subscription;

  constructor(protected firmaService: FirmaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.firmaService.query().subscribe((res: HttpResponse<IFirma[]>) => (this.firmas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFirmas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFirma): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFirmas(): void {
    this.eventSubscriber = this.eventManager.subscribe('firmaListModification', () => this.loadAll());
  }

  delete(firma: IFirma): void {
    const modalRef = this.modalService.open(FirmaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.firma = firma;
  }
}
