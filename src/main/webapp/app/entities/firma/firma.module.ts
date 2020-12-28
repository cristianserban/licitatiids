import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LicitatiiSharedModule } from 'app/shared/shared.module';
import { FirmaComponent } from './firma.component';
import { FirmaDetailComponent } from './firma-detail.component';
import { FirmaUpdateComponent } from './firma-update.component';
import { FirmaDeleteDialogComponent } from './firma-delete-dialog.component';
import { firmaRoute } from './firma.route';

@NgModule({
  imports: [LicitatiiSharedModule, RouterModule.forChild(firmaRoute)],
  declarations: [FirmaComponent, FirmaDetailComponent, FirmaUpdateComponent, FirmaDeleteDialogComponent],
  entryComponents: [FirmaDeleteDialogComponent],
})
export class LicitatiiFirmaModule {}
