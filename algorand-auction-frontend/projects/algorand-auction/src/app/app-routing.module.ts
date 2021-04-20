import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login'
  },
  { 
    path: 'login', 
    loadChildren: () => import('./features/login/login.module').then(m => m.LoginModule) 
  },
  { path: 'catalogue', loadChildren: () => import('./features/catalogue/catalogue.module').then(m => m.CatalogueModule) },
  {
    path: '**',
    redirectTo: 'login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
