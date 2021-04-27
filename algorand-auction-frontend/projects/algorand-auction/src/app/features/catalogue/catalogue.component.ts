import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';
import {Auction} from '../../models/auction.interface';
import {CatalogueService} from '../../services/catalogue.service';

@Component({
  selector: 'algo-auction-catalogue',
  templateUrl: './catalogue.component.html',
  styleUrls: ['./catalogue.component.scss'],
})
export class CatalogueComponent implements OnInit {
  readonly searchForm: FormGroup = new FormGroup({
    search: new FormControl('', []),
  });

  readonly auctions: Observable<Auction[]> = this.catalogueService.fetch();

  constructor(
    private catalogueService: CatalogueService,
  ) { }

  ngOnInit(): void {
  }
}
