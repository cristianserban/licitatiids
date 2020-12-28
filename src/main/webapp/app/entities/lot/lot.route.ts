import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILot, Lot } from 'app/shared/model/lot.model';
import { LotService } from './lot.service';
import { LotComponent } from './lot.component';
import { LotDetailComponent } from './lot-detail.component';
import { LotUpdateComponent } from './lot-update.component';

@Injectable({ providedIn: 'root' })
export class LotResolve implements Resolve<ILot> {
  constructor(private service: LotService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILot> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lot: HttpResponse<Lot>) => {
          if (lot.body) {
            return of(lot.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Lot());
  }
}

export const lotRoute: Routes = [
  {
    path: '',
    component: LotComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.lot.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LotDetailComponent,
    resolve: {
      lot: LotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.lot.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LotUpdateComponent,
    resolve: {
      lot: LotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.lot.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LotUpdateComponent,
    resolve: {
      lot: LotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.lot.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
