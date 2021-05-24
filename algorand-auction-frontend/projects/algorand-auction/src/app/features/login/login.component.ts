import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs';
import {LoginService} from '../../services/login.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'algo-auction-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit, OnDestroy {
  readonly loginForm: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  });
  readonly loading$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);
  hide: boolean = true;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private _snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.loading$.next(false);
  }

  ngOnDestroy(): void {
  }

  disableLoginBtn(): boolean {
    return !this.loginForm.dirty || !this.loginForm.valid;
  }

  login(): void {
    this.loading$.next(true);
    this.loginService.login({
      username: this.loginForm.get('username')?.value,
      password: this.loginForm.get('password')?.value,
    })
        .subscribe({
          next: (v) => {
            this.loading$.next(false);
            localStorage.setItem('token', v.token);
            localStorage.setItem('username', this.loginForm.get('username')?.value);
            this.router.navigate(['/auctions']);
          },
          error: (error) => {
            this.loginForm.get('password')?.reset();
            this._snackBar.open('Incorrect username or password', 'close');
            this.loading$.next(false);
          },
          complete: () => {},
        });
  }
}
