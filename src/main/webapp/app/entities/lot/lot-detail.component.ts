import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILot } from 'app/shared/model/lot.model';

@Component({
  selector: 'jhi-lot-detail',
  templateUrl: './lot-detail.component.html',
})
export class LotDetailComponent implements OnInit {
  lot: ILot | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lot }) => (this.lot = lot));
  }

  previousState(): void {
    window.history.back();
  }
}
