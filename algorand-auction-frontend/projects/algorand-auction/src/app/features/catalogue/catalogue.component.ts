import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {map, take, takeUntil, tap, withLatestFrom} from 'rxjs/operators';
import {Auction} from '../../models/auction.interface';
import {CatalogueService} from '../../services/catalogue.service';

@Component({
  selector: 'algo-auction-catalogue',
  templateUrl: './catalogue.component.html',
  styleUrls: ['./catalogue.component.scss'],
})
export class CatalogueComponent implements OnInit, OnDestroy {
  readonly unsubscribe$: Subject<void> = new Subject<void>();
  readonly auctions$: BehaviorSubject<Auction[]> = new BehaviorSubject<Auction[]>([]);

  readonly searchForm: FormGroup = new FormGroup({
    search: new FormControl('', []),
  });

  readonly catalogue$: Observable<Auction[]> = this.catalogueService.fetch().pipe(
      take(1),
      tap((catalogue) => this.auctions$.next(catalogue)),
  );

  readonly search$: Observable<any> | undefined = this.searchForm.get('search')?.valueChanges.pipe(
      takeUntil(this.unsubscribe$),
      withLatestFrom(this.catalogue$),
      map(([search, catalogue]) => {
        const result = catalogue.filter((auction) => {
          return auction.title.startsWith(search);
        });

        this.auctions$.next(result);
      }),
  );

  constructor(
    private catalogueService: CatalogueService,
  ) { }

  ngOnInit(): void {
    this.catalogue$.subscribe();
    this.search$?.subscribe();
  }

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
