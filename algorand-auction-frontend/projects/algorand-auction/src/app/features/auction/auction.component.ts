import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Observable, of, Subject, timer} from 'rxjs';
import {catchError, map, startWith, switchMap, tap} from 'rxjs/operators';
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

  readonly details$: Observable<Auction> = timer(0, 2000).pipe(
      switchMap(() => this.auctionDetailsService
          .fetch(Number(this.route.snapshot.paramMap.get('auctionId'))),
      ),
      map((details) => details.item),
  );

  readonly bids$: Observable<AuctionBid[]> = this.refreshBids.pipe(
      startWith({}),
      switchMap(() => {
        this.areBidsLoading = true;
        return this.bidsService.fetch(Number(this.route.snapshot.paramMap.get('auctionId'))).pipe(
            catchError((error) => {
              this.hasBidsError = true;
              return of([]);
            }),
        );
      }),
      map((bids) => bids.reverse()),
      tap((bids) => this.highestBid = bids[0]?.amount),
  );

  readonly bidForm: FormGroup = new FormGroup({
    bid: new FormControl('', [Validators.required]),
  });

  readonly placeBid$: Observable<any> = this.amount$.pipe(
      switchMap((amount) => this.placeBidService.place({
        amount,
        auctionId: this.auction?.id,
        token: localStorage.getItem('token'),
      })),
  );

  readonly displayedColumns: string[] = ['user', 'amount'];
  datasource: AuctionBid[] = [];
  highestBid: number = 0;
  auction: Auction | null = null;

  hasBidsError: boolean = false;
  areBidsLoading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private auctionDetailsService: AuctionDetailsService,
    private bidsService: BidsService,
    private placeBidService: PlaceBidService,
  ) { }

  ngOnInit() {
    this.details$.subscribe((details) => {
      this.auction = details;
    });
    this.placeBid$.subscribe((v) => this.refreshBids.next());
    this.bids$.subscribe((bids) => {
      this.areBidsLoading = false;
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
