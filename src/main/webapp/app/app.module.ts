import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { LicitatiidsSharedModule } from 'app/shared/shared.module';
import { LicitatiidsCoreModule } from 'app/core/core.module';
import { LicitatiidsAppRoutingModule } from './app-routing.module';
import { LicitatiidsHomeModule } from './home/home.module';
import { LicitatiidsEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    LicitatiidsSharedModule,
    LicitatiidsCoreModule,
    LicitatiidsHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    LicitatiidsEntityModule,
    LicitatiidsAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class LicitatiidsAppModule {}
