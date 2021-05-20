import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {PlaceBidRequest} from '../models/place-bid.interface';

@Injectable()
export class PlaceBidService {
  constructor(
        private http: HttpClient,
  ) { }

  place(placeBid: PlaceBidRequest): Observable<any> {
    const headers = {'Content-Type': 'application/json'};

    return this.http.post<any>(`/api/bid/place`, {...placeBid}, {headers})
        .pipe(catchError((error) => of({})));
  }
}
