import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {Auction} from '../models/auction.interface';
import {catalogue} from './mocks/catalogue.mock';


@Injectable()
export class CatalogueService {
  constructor(
        private http: HttpClient,
  ) { }

  fetch(): Observable<Auction[]> {
    return this.http.get<Auction[]>('/auctions/all').pipe(
        map((v) => catalogue),
        catchError((err) => {
          return of(catalogue);
        }),
    );
  }
}