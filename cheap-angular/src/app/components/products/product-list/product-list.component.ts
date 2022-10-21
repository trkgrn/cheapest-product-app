import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../../services/product.service";
import {PrimeNGConfig, SelectItem} from "primeng/api";
import {FilterElements} from "../../../model/filterelements";
import {SelectedFilter} from "../../../model/selectedfilter";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css','./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  products: any[]= [];
  totalProducts:any;
  sortOptions: SelectItem[]=[];
  sortOrder: any;
  sortField: any;
  sortKey:any;
  filterElements:FilterElements = {brandNames:[],operatingSystems:[],gpus:[],cpus:[],hdds:[],colors:[],screenSizes:[],rams:[]};
  selectedFilters:SelectedFilter= {brandName:[],operatingSystem:[],gpu:[],cpu:[],hdd:[],color:[],screenSize:[],ram:[]};

  constructor(private productService: ProductService,private primengConfig: PrimeNGConfig,
              public route:Router) { }

 async ngOnInit() {
  var filters:any = await this.productService.getFilterElements();
  this.filterElements = filters;
   var count:any = await this.productService.getProductCountByFilter(this.selectedFilters);
   this.totalProducts = count;
   console.log(this.totalProducts)
   var products:any =  await this.productService.getProductsByFilterAndPage(this.selectedFilters,0,24);
  this.products = products;

    this.sortOptions = [
      {label: 'Price High to Low', value: '!price'},
      {label: 'Price Low to High', value: 'price'}
    ];
  }

  first = 0;

  async onPageChange(event:any){
    let pageNo = event.page;
    let pageSize = event.rows;
    let productListByPage : any = await this.productService.getProductsByFilterAndPage(this.selectedFilters,pageNo,pageSize)
    this.products = productListByPage;
  }

 async filterChange(){
    console.log(this.selectedFilters)
    let count:any = await this.productService.getProductCountByFilter(this.selectedFilters);
    this.totalProducts = count;
    let products:any = await this.productService.getProductsByFilterAndPage(this.selectedFilters,0,24);
    this.products = products;
  }

  filterStore(store:any){
    var str:any[] = []
    return str;
  }


  onSortChange(event:any) {
    let value = event.value;

    if (value.indexOf('!') === 0) {
      this.sortOrder = -1;
      this.sortField = value.substring(1, value.length);
    }
    else {
      this.sortOrder = 1;
      this.sortField = value;
    }
  }
}
