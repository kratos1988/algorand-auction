import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Credentials} from '../models/credentials.interface';

@Injectable()
export class LoginService {
  constructor(
    private http: HttpClient,
  ) {}

  login(credentials: Credentials): Observable<any> {
    const headers = {'Content-Type': 'application/json'};

    return this.http.post<any>('/api/authenticate', {...credentials}, {headers});
  }
}
