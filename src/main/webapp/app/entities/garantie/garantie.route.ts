import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGarantie, Garantie } from 'app/shared/model/garantie.model';
import { GarantieService } from './garantie.service';
import { GarantieComponent } from './garantie.component';
import { GarantieDetailComponent } from './garantie-detail.component';
import { GarantieUpdateComponent } from './garantie-update.component';

@Injectable({ providedIn: 'root' })
export class GarantieResolve implements Resolve<IGarantie> {
  constructor(private service: GarantieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGarantie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((garantie: HttpResponse<Garantie>) => {
          if (garantie.body) {
            return of(garantie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Garantie());
  }
}

export const garantieRoute: Routes = [
  {
    path: '',
    component: GarantieComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.garantie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GarantieDetailComponent,
    resolve: {
      garantie: GarantieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.garantie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GarantieUpdateComponent,
    resolve: {
      garantie: GarantieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.garantie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GarantieUpdateComponent,
    resolve: {
      garantie: GarantieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.garantie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
