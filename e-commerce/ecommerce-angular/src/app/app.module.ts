import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import {PasswordModule} from "primeng/password";
import {CheckboxModule} from "primeng/checkbox";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {InputTextModule} from "primeng/inputtext";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthService} from "./services/auth.service";
import {LoginGuard} from "./components/auth/login/login.guard";
import {ToastModule} from "primeng/toast";
import {ConfirmationService, MessageService} from "primeng/api";
import { HomeComponent } from './components/home/home.component';
import {HttpService} from "./services/http.service";
import {JwtInterceptor} from "./JwtInterceptor";
import { UnauthorizedComponent } from './components/error/unauthorized/unauthorized.component';
import { ForbiddenComponent } from './components/error/forbidden/forbidden.component';
import { NotfoundComponent } from './components/error/notfound/notfound.component';
import { TestComponent } from './components/test/test.component';
import {DialogModule} from "primeng/dialog";
import {TableModule} from "primeng/table";
import {PaginatorModule} from "primeng/paginator";
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import {ProductService} from "./services/product.service";
import {ListboxModule} from "primeng/listbox";
import {DataViewModule} from "primeng/dataview";
import {RatingModule} from "primeng/rating";
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { ProductManageComponent } from './components/product-manage/product-manage.component';
import {ToolbarModule} from "primeng/toolbar";
import {RadioButtonModule} from "primeng/radiobutton";
import {ConfirmDialogModule} from "primeng/confirmdialog";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    UnauthorizedComponent,
    ForbiddenComponent,
    NotfoundComponent,
    TestComponent,
    NavbarComponent,
    ProductListComponent,
    ProductDetailsComponent,
    ProductManageComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    PasswordModule,
    CheckboxModule,
    FormsModule,
    ButtonModule,
    RippleModule,
    InputTextModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastModule,
    DialogModule,
    TableModule,
    PaginatorModule,
    ListboxModule,
    DataViewModule,
    RatingModule,
    ToolbarModule,
    RadioButtonModule,
    ConfirmDialogModule
  ],
  providers: [AuthService,LoginGuard,MessageService,ConfirmationService,ProductService,HttpService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },],
  bootstrap: [AppComponent]
})
export class AppModule { }
