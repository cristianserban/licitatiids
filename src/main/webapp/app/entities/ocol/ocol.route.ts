import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOcol, Ocol } from 'app/shared/model/ocol.model';
import { OcolService } from './ocol.service';
import { OcolComponent } from './ocol.component';
import { OcolDetailComponent } from './ocol-detail.component';
import { OcolUpdateComponent } from './ocol-update.component';

@Injectable({ providedIn: 'root' })
export class OcolResolve implements Resolve<IOcol> {
  constructor(private service: OcolService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOcol> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ocol: HttpResponse<Ocol>) => {
          if (ocol.body) {
            return of(ocol.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ocol());
  }
}

export const ocolRoute: Routes = [
  {
    path: '',
    component: OcolComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.ocol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OcolDetailComponent,
    resolve: {
      ocol: OcolResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.ocol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OcolUpdateComponent,
    resolve: {
      ocol: OcolResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.ocol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OcolUpdateComponent,
    resolve: {
      ocol: OcolResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'licitatiiApp.ocol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
