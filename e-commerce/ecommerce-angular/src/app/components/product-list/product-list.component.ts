import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../services/product.service";
import {PrimeNGConfig, SelectItem} from "primeng/api";
import {FilterElements} from "../../model/filterelements";
import {SelectedFilter} from "../../model/selectedfilter";
import {ActivatedRoute} from "@angular/router";

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
  first = 0;
  filterElements:FilterElements = {brandNames:[],operatingSystems:[],gpus:[],cpus:[],hdds:[],colors:[],screenSizes:[],rams:[]};
  selectedFilters:SelectedFilter= {brandName:[],operatingSystem:[],gpu:[],cpu:[],hdd:[],color:[],screenSize:[],ram:[],orderBy:{},searchTitle:''};

  constructor(private productService: ProductService,private primengConfig: PrimeNGConfig,
              private route: ActivatedRoute) { }

 async ngOnInit() {
    let pageNo:any = this.route.snapshot.paramMap.get('pageNo')
    let filters:any = await this.productService.getFilterElements();
    this.filterElements = filters;

   if (pageNo){
     let count:any = await this.productService.getProductCountByFilter(this.selectedFilters);
     this.totalProducts = count;
     let products:any =  await this.productService.getProductsByFilterAndPage(this.selectedFilters,pageNo-1,24);
     this.products = products;
     if (products.length == 0)
       this.totalProducts = 0
   }
   else{
     let count:any = await this.productService.getProductCountByFilter(this.selectedFilters);
     this.totalProducts = count;
     let products:any =  await this.productService.getProductsByFilterAndPage(this.selectedFilters,0,24);
     this.products = products;
   }

    this.sortOptions = [
      {label: 'Fiyata göre azalan', value:{field:'product_price',type:'DESC'}},
      {label: 'Fiyata göre artan', value: {field:'product_price',type:'ASC'}},
      {label: 'Puana göre azalan', value: {field:'product_score',type:'DESC'}},
      {label: 'Puana göre artan' , value: {field:'product_score',type:'ASC'}}
    ];
  }



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
