import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'lot',
        loadChildren: () => import('./lot/lot.module').then(m => m.LicitatiiLotModule),
      },
      {
        path: 'oferta',
        loadChildren: () => import('./oferta/oferta.module').then(m => m.LicitatiiOfertaModule),
      },
      {
        path: 'licitatie',
        loadChildren: () => import('./licitatie/licitatie.module').then(m => m.LicitatiiLicitatieModule),
      },
      {
        path: 'firma',
        loadChildren: () => import('./firma/firma.module').then(m => m.LicitatiiFirmaModule),
      },
      {
        path: 'garantie',
        loadChildren: () => import('./garantie/garantie.module').then(m => m.LicitatiiGarantieModule),
      },
      {
        path: 'ocol',
        loadChildren: () => import('./ocol/ocol.module').then(m => m.LicitatiiOcolModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LicitatiiEntityModule {}
