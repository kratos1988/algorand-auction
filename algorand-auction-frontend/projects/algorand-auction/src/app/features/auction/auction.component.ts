import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AuctionDetailsService} from '../../services/auction-detail.service';

@Component({
  selector: 'algo-auction-auction',
  templateUrl: './auction.component.html',
  styleUrls: ['./auction.component.scss'],
})
export class AuctionComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private auctionDetailsService: AuctionDetailsService,
  ) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;

    this.auctionDetailsService
        .fetch(Number(routeParams.get('auctionId')))
        .subscribe((auction) => {
          console.log(auction);
        });
  }
}
