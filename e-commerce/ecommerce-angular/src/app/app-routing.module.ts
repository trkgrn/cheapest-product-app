import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/auth/login/login.component";
import {LoginGuard} from "./components/auth/login/login.guard";
import {RegisterComponent} from "./components/auth/register/register.component";
import {HomeComponent} from "./components/home/home.component";
import {UnauthorizedComponent} from "./components/error/unauthorized/unauthorized.component";
import {ForbiddenComponent} from "./components/error/forbidden/forbidden.component";
import {NotfoundComponent} from "./components/error/notfound/notfound.component";
import {TestComponent} from "./components/test/test.component";
import {ProductListComponent} from "./components/product-list/product-list.component";
import {ProductDetailsComponent} from "./components/product-details/product-details.component";

const routes: Routes = [
  {path:"",redirectTo:"products",pathMatch:"full"},
  {path:"login",component:LoginComponent},
  {path:"register",component:RegisterComponent},
  {path:"home",component:HomeComponent,canActivate:[LoginGuard],data:{roles:['KULLANICI','ADMIN']}},
  {path:"products",component:ProductListComponent},
  {path:"products/page/:pageNo",component:ProductListComponent},
  {path:"product/:productCode",component:ProductDetailsComponent},
  {path:"product",component:ProductDetailsComponent},
  {path:"unauthorized",component:UnauthorizedComponent},
  {path:"forbidden",component:ForbiddenComponent},
  {path:"notfound",component:NotfoundComponent},
  {path:"test",component:TestComponent,canActivate:[LoginGuard],data:{roles: ['KULLANICI']}},
  {path:"**",redirectTo:"notfound",pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
