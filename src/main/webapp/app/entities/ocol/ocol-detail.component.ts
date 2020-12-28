import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOcol } from 'app/shared/model/ocol.model';

@Component({
  selector: 'jhi-ocol-detail',
  templateUrl: './ocol-detail.component.html',
})
export class OcolDetailComponent implements OnInit {
  ocol: IOcol | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ocol }) => (this.ocol = ocol));
  }

  previousState(): void {
    window.history.back();
  }
}
