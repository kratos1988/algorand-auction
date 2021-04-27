import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {BehaviorSubject, Subject} from 'rxjs';
import {LoginService} from '../../services/login.service';
import {delay, takeUntil} from 'rxjs/operators';

@Component({
  selector: 'algo-auction-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit, OnDestroy {
  readonly loginForm: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  });
  private readonly unsubscribe$: Subject<void> = new Subject<void>();
  readonly loading$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);
  hide: boolean = true;

  constructor(
    private loginService: LoginService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    setTimeout(() => {
      this.loading$.next(false);
    }, 2000);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  disableLoginBtn(): boolean {
    return !this.loginForm.dirty || !this.loginForm.valid;
  }

  login(): void {
    this.loading$.next(true);
    this.loginService.login()
        .pipe(
            takeUntil(this.unsubscribe$),
            delay(2000),
        )
        .subscribe({
          next: (v) => {
            this.router.navigate(['/catalogue']);
          },
          error: (err) => {
            this.loading$.next(false);
          },
          complete: () => {},
        });
  }
}
