import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LicitatiiSharedModule } from 'app/shared/shared.module';
import { LicitatieComponent } from './licitatie.component';
import { LicitatieDetailComponent } from './licitatie-detail.component';
import { LicitatieUpdateComponent } from './licitatie-update.component';
import { LicitatieDeleteDialogComponent } from './licitatie-delete-dialog.component';
import { licitatieRoute } from './licitatie.route';

@NgModule({
  imports: [LicitatiiSharedModule, RouterModule.forChild(licitatieRoute)],
  declarations: [LicitatieComponent, LicitatieDetailComponent, LicitatieUpdateComponent, LicitatieDeleteDialogComponent],
  entryComponents: [LicitatieDeleteDialogComponent],
})
export class LicitatiiLicitatieModule {}
