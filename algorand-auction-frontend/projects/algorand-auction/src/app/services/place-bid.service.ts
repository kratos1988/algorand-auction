import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {PlaceBidRequest} from '../models/place-bid.interface';

@Injectable()
export class PlaceBidService {
  constructor(
        private http: HttpClient,
        private _snackBar: MatSnackBar,
  ) { }

  place(placeBid: PlaceBidRequest): Observable<any> {
    const headers = {'Content-Type': 'application/json'};

    return this.http.post<any>(`/api/bid/place`, {...placeBid}, {headers}).pipe(
        catchError((error) => {
          this._snackBar.open('An error occurred when placing the bid. Please try again.', 'close');
          return of(null);
        }),
    );
  }
}
