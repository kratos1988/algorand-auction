import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'algo-auction-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  constructor(
    private router: Router,
  ) {

  }

  ngOnInit() {

  }

  navigateToAuctions() {
    this.router.navigate(['/auctions']);
  }
}
