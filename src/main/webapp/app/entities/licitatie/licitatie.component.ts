import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILicitatie } from 'app/shared/model/licitatie.model';
import { LicitatieService } from './licitatie.service';
import { LicitatieDeleteDialogComponent } from './licitatie-delete-dialog.component';

@Component({
  selector: 'jhi-licitatie',
  templateUrl: './licitatie.component.html',
})
export class LicitatieComponent implements OnInit, OnDestroy {
  licitaties?: ILicitatie[];
  eventSubscriber?: Subscription;

  constructor(protected licitatieService: LicitatieService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.licitatieService.query().subscribe((res: HttpResponse<ILicitatie[]>) => (this.licitaties = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLicitaties();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILicitatie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLicitaties(): void {
    this.eventSubscriber = this.eventManager.subscribe('licitatieListModification', () => this.loadAll());
  }

  delete(licitatie: ILicitatie): void {
    const modalRef = this.modalService.open(LicitatieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.licitatie = licitatie;
  }
}
