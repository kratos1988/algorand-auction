import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AuctionRoutingModule} from './auction-routing.module';
import {AuctionComponent} from './auction.component';
import {AuctionDetailsService} from '../../services/auction-detail.service';
import {HttpClientModule} from '@angular/common/http';
import {BidsService} from '../../services/bids.service';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {ReactiveFormsModule} from '@angular/forms';
import {PlaceBidService} from '../../services/place-bid.service';

const ANGULAR_MATERIAL_MODULES = [
  MatDividerModule,
  MatFormFieldModule,
  MatInputModule,
  MatIconModule,
  MatButtonModule,
];

@NgModule({
  declarations: [
    AuctionComponent,
  ],
  imports: [
    CommonModule,
    AuctionRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    ...ANGULAR_MATERIAL_MODULES,
  ],
  providers: [
    AuctionDetailsService,
    BidsService,
    PlaceBidService,
  ],
})
export class AuctionModule { }

