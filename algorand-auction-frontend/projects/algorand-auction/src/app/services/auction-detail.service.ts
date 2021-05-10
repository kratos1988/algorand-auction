import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AuctionDetails} from '../models/auction.interface';


@Injectable()
export class AuctionDetailsService {
  constructor(
        private http: HttpClient,
  ) { }

  fetch(auctionId: number): Observable<AuctionDetails> {
    return this.http.get<AuctionDetails>(`/api/auctions/${auctionId}`);
  }
}
