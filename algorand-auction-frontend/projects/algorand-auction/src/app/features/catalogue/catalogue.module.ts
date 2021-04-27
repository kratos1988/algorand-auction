import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';

import {MatCardModule} from '@angular/material/card';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatIconModule} from '@angular/material/icon';

import {ReactiveFormsModule} from '@angular/forms';

import {CatalogueRoutingModule} from './catalogue-routing.module';
import {CatalogueComponent} from './catalogue.component';
import {CatalogueService} from '../../services/catalogue.service';
import {HttpClientModule} from '@angular/common/http';
import {AuctionCardComponent} from '../../components/auction-card/auction-card.component';
import {MatChipsModule} from '@angular/material/chips';

@NgModule({
  declarations: [
    CatalogueComponent,
    AuctionCardComponent,
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    CatalogueRoutingModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatChipsModule,
    ReactiveFormsModule,
  ],
  providers: [
    CatalogueService,
    DatePipe,
  ],
})
export class CatalogueModule { }
