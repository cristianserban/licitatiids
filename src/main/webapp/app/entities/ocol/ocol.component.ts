import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOcol } from 'app/shared/model/ocol.model';
import { OcolService } from './ocol.service';
import { OcolDeleteDialogComponent } from './ocol-delete-dialog.component';

@Component({
  selector: 'jhi-ocol',
  templateUrl: './ocol.component.html',
})
export class OcolComponent implements OnInit, OnDestroy {
  ocols?: IOcol[];
  eventSubscriber?: Subscription;

  constructor(protected ocolService: OcolService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.ocolService.query().subscribe((res: HttpResponse<IOcol[]>) => (this.ocols = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOcols();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOcol): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOcols(): void {
    this.eventSubscriber = this.eventManager.subscribe('ocolListModification', () => this.loadAll());
  }

  delete(ocol: IOcol): void {
    const modalRef = this.modalService.open(OcolDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ocol = ocol;
  }
}
