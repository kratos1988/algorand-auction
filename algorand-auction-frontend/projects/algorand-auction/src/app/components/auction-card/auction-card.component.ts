import {DatePipe} from '@angular/common';
import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {interval, Subject} from 'rxjs';
import {startWith, takeUntil, tap} from 'rxjs/operators';
import {Auction} from '../../models/auction.interface';

@Component({
  selector: 'algo-auction-card',
  templateUrl: './auction-card.component.html',
  styleUrls: ['./auction-card.component.scss'],
})
export class AuctionCardComponent implements OnInit, OnDestroy {
    @Input()
    detail!: Auction;
    public expirationDate: string | null = null;
    private unsubscribe$: Subject<void> = new Subject();
    private readonly DAY_IN_MS = 1000 * 60 * 60 * 24;

    constructor(
      private datePipe: DatePipe,
    ) { }

    ngOnInit(): void {
      interval(1000).pipe(
          takeUntil(this.unsubscribe$),
          startWith(0),
          tap(() => {
            const detailExpiryDate = new Date(this.detail.expirationDate).getTime() - Date.now();
            if (detailExpiryDate < 0) {
              this.expirationDate = null;
            } else if (detailExpiryDate > this.DAY_IN_MS) {
              this.expirationDate = this.datePipe.transform(this.detail.expirationDate, 'medium');
            } else {
              const ded = new Date(detailExpiryDate);
              this.expirationDate = this.datePipe.transform(ded, 'hh:mm:ss');
            }
          }),
      ).subscribe();
    }

    ngOnDestroy() {
      this.unsubscribe$.next();
      this.unsubscribe$.complete();
    }

    goToAuction(event: MouseEvent) {
      console.log(event);
    }
}
