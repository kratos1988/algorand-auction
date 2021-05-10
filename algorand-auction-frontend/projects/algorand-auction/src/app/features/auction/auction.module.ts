import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AuctionRoutingModule} from './auction-routing.module';
import {AuctionComponent} from './auction.component';
import {AuctionDetailsService} from '../../services/auction-detail.service';
import {HttpClientModule} from '@angular/common/http';


@NgModule({
  declarations: [
    AuctionComponent,
  ],
  imports: [
    CommonModule,
    AuctionRoutingModule,
    HttpClientModule,
  ],
  providers: [AuctionDetailsService],
})
export class AuctionModule { }

