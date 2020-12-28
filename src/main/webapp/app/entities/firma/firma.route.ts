import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFirma, Firma } from 'app/shared/model/firma.model';
import { FirmaService } from './firma.service';
import { FirmaComponent } from './firma.component';
import { FirmaDetailComponent } from './firma-detail.component';
import { FirmaUpdateComponent } from './firma-update.component';

@Injectable({ providedIn: 'root' })
export class FirmaResolve implements Resolve<IFirma> {
  constructor(private service: FirmaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFirma> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((firma: HttpResponse<Firma>) => {
          if (firma.body) {
            return of(firma.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Firma());
  }
}

export const firmaRoute: Routes = [
  {
    path: '',
    component: FirmaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.firma.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FirmaDetailComponent,
    resolve: {
      firma: FirmaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.firma.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FirmaUpdateComponent,
    resolve: {
      firma: FirmaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.firma.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FirmaUpdateComponent,
    resolve: {
      firma: FirmaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.firma.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
