import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOferta } from 'app/shared/model/oferta.model';
import { OfertaService } from './oferta.service';
import { OfertaDeleteDialogComponent } from './oferta-delete-dialog.component';

@Component({
  selector: 'jhi-oferta',
  templateUrl: './oferta.component.html',
})
export class OfertaComponent implements OnInit, OnDestroy {
  ofertas?: IOferta[];
  eventSubscriber?: Subscription;

  constructor(protected ofertaService: OfertaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.ofertaService.query().subscribe((res: HttpResponse<IOferta[]>) => (this.ofertas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOfertas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOferta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOfertas(): void {
    this.eventSubscriber = this.eventManager.subscribe('ofertaListModification', () => this.loadAll());
  }

  delete(oferta: IOferta): void {
    const modalRef = this.modalService.open(OfertaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.oferta = oferta;
  }
}
