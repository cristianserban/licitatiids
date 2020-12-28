import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILicitatie, Licitatie } from 'app/shared/model/licitatie.model';
import { LicitatieService } from './licitatie.service';
import { LicitatieComponent } from './licitatie.component';
import { LicitatieDetailComponent } from './licitatie-detail.component';
import { LicitatieUpdateComponent } from './licitatie-update.component';

@Injectable({ providedIn: 'root' })
export class LicitatieResolve implements Resolve<ILicitatie> {
  constructor(private service: LicitatieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILicitatie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((licitatie: HttpResponse<Licitatie>) => {
          if (licitatie.body) {
            return of(licitatie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Licitatie());
  }
}

export const licitatieRoute: Routes = [
  {
    path: '',
    component: LicitatieComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.licitatie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LicitatieDetailComponent,
    resolve: {
      licitatie: LicitatieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.licitatie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LicitatieUpdateComponent,
    resolve: {
      licitatie: LicitatieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.licitatie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LicitatieUpdateComponent,
    resolve: {
      licitatie: LicitatieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.licitatie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
