import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';

@Injectable()
export class LoginService {
  constructor() {}

  login(): Observable<any> {
    return of({
      status: 200,
    });
  }
}
