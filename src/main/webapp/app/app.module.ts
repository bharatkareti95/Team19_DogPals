import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DogpalsPresentationSharedModule } from 'app/shared/shared.module';
import { DogpalsPresentationCoreModule } from 'app/core/core.module';
import { DogpalsPresentationAppRoutingModule } from './app-routing.module';
import { DogpalsPresentationHomeModule } from './home/home.module';
import { DogpalsPresentationEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    DogpalsPresentationSharedModule,
    DogpalsPresentationCoreModule,
    DogpalsPresentationHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DogpalsPresentationEntityModule,
    DogpalsPresentationAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class DogpalsPresentationAppModule {}
