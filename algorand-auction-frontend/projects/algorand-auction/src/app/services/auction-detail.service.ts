import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuctionDetails} from '../models/auction.interface';
import {auction} from './mocks/auction-details.mock';


@Injectable()
export class AuctionDetailsService {
  constructor(
        private http: HttpClient,
  ) { }

  fetch(auctionId: number): Observable<AuctionDetails> {
    return this.http.get<AuctionDetails>(`/api/auctions/${auctionId}`)
        .pipe(
            catchError(() => of(auction)),
        );
  }
}
