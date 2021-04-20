import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";

@Injectable()
export class LoginService {
    constructor() {}

    login() {
        return of({
            status: 200
        })
    }
}