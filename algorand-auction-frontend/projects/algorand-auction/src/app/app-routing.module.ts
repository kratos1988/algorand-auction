import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuardService} from './services/auth-guard.service';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login',
  },
  {
    path: 'login',
    loadChildren: () => import('./features/login/login.module').then((m) => m.LoginModule),
  },
  {
    path: 'catalogue',
    loadChildren: () => import('./features/catalogue/catalogue.module').then((m) => m.CatalogueModule),
    canActivate: [AuthGuardService],
  },
  {
    path: 'auction',
    loadChildren: () => import('./features/auction/auction.module').then((m) => m.AuctionModule),
    canActivate: [AuthGuardService],
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
