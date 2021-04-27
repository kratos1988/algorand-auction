import {Component, Input, OnInit} from '@angular/core';
import {Auction} from '../../models/auction.interface';

@Component({
  selector: 'algo-auction-card',
  templateUrl: './auction-card.component.html',
  styleUrls: ['./auction-card.component.scss'],
})
export class AuctionCardComponent implements OnInit {
    @Input()
    detail!: Auction;

    constructor() { }

    ngOnInit(): void {

    }
}
