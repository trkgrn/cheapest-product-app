import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductService} from "../../../services/product.service";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css','./product-details.component.scss']
})
export class ProductDetailsComponent implements OnInit {

  seenProduct:any;
  seenProductStores:any;
  constructor(private route:ActivatedRoute,private productService:ProductService) { }

  async ngOnInit(){
    let productCode:any = this.route.snapshot.paramMap.get('productCode');
    if(productCode){
      let productTemp:any = await this.productService.getProductByProductCode(productCode.replaceAll('^','/'));
      this.seenProduct = productTemp.product!;
      this.seenProductStores = productTemp.storeList;
    }
  }

}
