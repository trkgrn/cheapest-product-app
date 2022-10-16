import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpService:HttpService) { }

  getProductsByPage(pageNo:any,pageSize:any){
    return this.httpService.get("product/getAllByPage?pageNo="+pageNo+"&pageSize="+pageSize);
  }
  getProductsByFilterAndPage(filter:any,pageNo:any,pageSize:any){
    return this.httpService.post("product/getAllByFilterAndPage?pageNo="+pageNo+"&pageSize="+pageSize,filter);
  }

  getProductCountByFilter(filter:any){
    return this.httpService.post("product/countByFilter",filter);
  }

  getProductCount(){
    return this.httpService.get("product/count");
  }

  getProductByProductCode(productCode:any){
    return this.httpService.get("product/getByProductCode?productCode="+productCode);
  }

  getFilterElements(){
    return this.httpService.get(("product/getFilterElements"));
  }


}
