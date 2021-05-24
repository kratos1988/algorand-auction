import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {map, startWith, switchMap, tap, withLatestFrom} from 'rxjs/operators';
import {Auction} from '../../models/auction.interface';
import {AuctionBid} from '../../models/bid.interface';
import {AuctionDetailsService} from '../../services/auction-detail.service';
import {BidsService} from '../../services/bids.service';
import {PlaceBidService} from '../../services/place-bid.service';

@Component({
  selector: 'algo-auction-auction',
  templateUrl: './auction.component.html',
  styleUrls: ['./auction.component.scss'],
})
export class AuctionComponent implements OnInit, OnDestroy {
  readonly refreshBids: Subject<void> = new Subject();
  readonly unsubscribe$: Subject<void> = new Subject();
  readonly amount$: Subject<number> = new Subject();

  readonly details$: Observable<Auction> = this.auctionDetailsService
      .fetch(Number(this.route.snapshot.paramMap.get('auctionId')))
      .pipe(
          map((details) => details.item),
      );
  readonly bids$: Observable<AuctionBid[]> = this.refreshBids.pipe(
      startWith({}),
      switchMap(() => this.bidsService.fetch(Number(this.route.snapshot.paramMap.get('auctionId')))),
      map((bids) => bids.reverse()),
      tap((bids) => this.highestBid = bids[0]?.amount),
  );

  readonly bidForm: FormGroup = new FormGroup({
    bid: new FormControl('', [Validators.required]),
  });

  readonly placeBid$: Observable<any> = this.amount$.pipe(
      withLatestFrom(this.details$),
      switchMap(([amount, details]) => this.placeBidService.place({
        amount,
        auctionId: details.id,
        userName: localStorage.getItem('username') || '',
      })),
  );

  readonly displayedColumns: string[] = ['user', 'amount'];
  datasource: AuctionBid[] = [];
  highestBid: number = 0;

  constructor(
    private route: ActivatedRoute,
    private auctionDetailsService: AuctionDetailsService,
    private bidsService: BidsService,
    private placeBidService: PlaceBidService,
  ) { }

  ngOnInit() {
    this.placeBid$.subscribe((v) => this.refreshBids.next());
    this.bids$.subscribe((bids) => {
      this.datasource = bids;
    });
  }

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  bid() {
    this.amount$.next(this.bidForm.get('bid')?.value);
  }
}
