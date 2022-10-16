import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductService} from "../../services/product.service";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  seenProduct:any;
  constructor(private route:ActivatedRoute,private productService:ProductService) { }

 async ngOnInit(){
    let productCode:any = this.route.snapshot.paramMap.get('productCode');
    if(productCode){
      let product:any = await this.productService.getProductByProductCode(productCode.replaceAll('^','/'));
      this.seenProduct = product;
    }
  }

}
