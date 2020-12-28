import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LicitatiiSharedModule } from 'app/shared/shared.module';
import { OcolComponent } from './ocol.component';
import { OcolDetailComponent } from './ocol-detail.component';
import { OcolUpdateComponent } from './ocol-update.component';
import { OcolDeleteDialogComponent } from './ocol-delete-dialog.component';
import { ocolRoute } from './ocol.route';

@NgModule({
  imports: [LicitatiiSharedModule, RouterModule.forChild(ocolRoute)],
  declarations: [OcolComponent, OcolDetailComponent, OcolUpdateComponent, OcolDeleteDialogComponent],
  entryComponents: [OcolDeleteDialogComponent],
})
export class LicitatiiOcolModule {}
