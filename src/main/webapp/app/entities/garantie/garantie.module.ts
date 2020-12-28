import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LicitatiiSharedModule } from 'app/shared/shared.module';
import { GarantieComponent } from './garantie.component';
import { GarantieDetailComponent } from './garantie-detail.component';
import { GarantieUpdateComponent } from './garantie-update.component';
import { GarantieDeleteDialogComponent } from './garantie-delete-dialog.component';
import { garantieRoute } from './garantie.route';

@NgModule({
  imports: [LicitatiiSharedModule, RouterModule.forChild(garantieRoute)],
  declarations: [GarantieComponent, GarantieDetailComponent, GarantieUpdateComponent, GarantieDeleteDialogComponent],
  entryComponents: [GarantieDeleteDialogComponent],
})
export class LicitatiiGarantieModule {}
