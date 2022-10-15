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

  getProductCount(){
    return this.httpService.get("product/count");
  }

  getFilterElements(){
    return this.httpService.get(("product/getFilterElements"));
  }


}
