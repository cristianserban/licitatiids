import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFirma } from 'app/shared/model/firma.model';

@Component({
  selector: 'jhi-firma-detail',
  templateUrl: './firma-detail.component.html',
})
export class FirmaDetailComponent implements OnInit {
  firma: IFirma | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ firma }) => (this.firma = firma));
  }

  previousState(): void {
    window.history.back();
  }
}
