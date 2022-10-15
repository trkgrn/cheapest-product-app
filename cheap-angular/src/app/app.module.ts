import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {PasswordModule} from "primeng/password";
import {CheckboxModule} from "primeng/checkbox";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {InputTextModule} from "primeng/inputtext";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {HttpService} from "./services/http.service";
import { UnauthorizedComponent } from './components/error/unauthorized/unauthorized.component';
import { ForbiddenComponent } from './components/error/forbidden/forbidden.component';
import { NotfoundComponent } from './components/error/notfound/notfound.component';
import { TestComponent } from './components/test/test.component';
import {DialogModule} from "primeng/dialog";
import {TableModule} from "primeng/table";
import {PaginatorModule} from "primeng/paginator";
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProductListComponent } from './components/products/product-list/product-list.component';
import {DataViewModule} from "primeng/dataview";
import {RatingModule} from "primeng/rating";
import {ListboxModule} from "primeng/listbox";

@NgModule({
  declarations: [
    AppComponent,
    UnauthorizedComponent,
    ForbiddenComponent,
    NotfoundComponent,
    TestComponent,
    NavbarComponent,
    ProductListComponent
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
        DataViewModule,
        RatingModule,
        ListboxModule
    ],
  providers: [MessageService,HttpService],
  bootstrap: [AppComponent]
})
export class AppModule { }
