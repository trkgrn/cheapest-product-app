import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UnauthorizedComponent} from "./components/error/unauthorized/unauthorized.component";
import {ForbiddenComponent} from "./components/error/forbidden/forbidden.component";
import {NotfoundComponent} from "./components/error/notfound/notfound.component";
import {TestComponent} from "./components/test/test.component";
import {ProductListComponent} from "./components/products/product-list/product-list.component";

const routes: Routes = [
  {path:"products",component:ProductListComponent},
  {path:"",redirectTo:"products",pathMatch:"full"},
  {path:"unauthorized",component:UnauthorizedComponent},
  {path:"forbidden",component:ForbiddenComponent},
  {path:"notfound",component:NotfoundComponent},
  {path:"test",component:TestComponent},
  {path:"**",redirectTo:"notfound",pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
