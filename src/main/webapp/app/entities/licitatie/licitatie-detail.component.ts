import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILicitatie } from 'app/shared/model/licitatie.model';

@Component({
  selector: 'jhi-licitatie-detail',
  templateUrl: './licitatie-detail.component.html',
})
export class LicitatieDetailComponent implements OnInit {
  licitatie: ILicitatie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licitatie }) => (this.licitatie = licitatie));
  }

  previousState(): void {
    window.history.back();
  }
}
