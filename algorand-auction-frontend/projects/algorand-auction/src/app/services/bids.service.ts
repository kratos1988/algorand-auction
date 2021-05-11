import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuctionBid} from '../models/bid.interface';
import {bids} from './mocks/auction-bids.mock';

@Injectable()
export class BidsService {
  constructor(
        private http: HttpClient,
  ) { }

  fetch(auctionId: number): Observable<AuctionBid[]> {
    return this.http.get<AuctionBid[]>(`/api/auctions/${auctionId}/bids`)
        .pipe(
            catchError(() => of(bids)),
        );
  }
}
