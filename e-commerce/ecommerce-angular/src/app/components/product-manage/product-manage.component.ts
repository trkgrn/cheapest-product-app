import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../services/product.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {Product} from "../../model/product";

@Component({
  selector: 'app-product-manage',
  templateUrl: './product-manage.component.html',
  styleUrls: ['./product-manage.component.css']
})
export class ProductManageComponent implements OnInit {

  products: any[] = [];

  product: Product = new Product();

  selectedProducts: any[] = [];

  productDialog: boolean = false;

  constructor(private productService: ProductService, private messageService: MessageService,
              private confirmationService: ConfirmationService) {
  }

  async ngOnInit() {
    let productList: any = await this.productService.getAllProduct();
    this.products = productList;
  }

  openNew() {
    this.product = new Product();
    this.productDialog = true;
  }

  editProduct(product: any) {
    this.product = {...product};
    this.productDialog = true;
  }

  isValid() {
    if (!this.product.productTitle || !this.product.productBrand || !this.product.productBrand || !this.product.productImage
      || !this.product.color || !this.product.gpu || !this.product.cpu || !this.product.hdd || !this.product.ram
      || !this.product.usageType || !this.product.operatingSystem || !this.product.screenSize || !this.product.productCode
      || !this.product.weight || !this.product.productPrice || this.product.productScore == null)
      return false;
    return true;
  }

 async deleteProduct(product: any) {
   this.product = {...product};
    this.confirmationService.confirm({
      message: 'Bu ürünü silmek istediğinize emin misiniz ? ',
      header: 'Uyarı',
      icon: 'pi pi-exclamation-triangle',
      accept: async () => {
        await this.productService.deleteProduct(this.product.productId)
          .then((res: any) => {
            this.messageService.add({severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000});
          })
          .catch((err: any) => {
            console.log(err)
            this.messageService.add({severity: 'error', summary: 'Error', detail: 'Product Not Deleted', life: 3000});
          });
        let productList:any = await this.productService.getAllProduct();
        this.products = productList;
      }
    });
  } // düzenle

  hideDialog() {
    this.productDialog = false;
  }

  async saveProduct() {
    if (this.product.productId) {
      await this.productService.updateProduct(this.product)
        .then((res: any) => {
          console.log(res)
          this.messageService.add({severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000});
        })
        .catch((err: any) => {
          console.log(err)
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'Product Not Updated', life: 3000});
        });
    } else {
      this.product.productId = 0;
      await this.productService.createProduct(this.product)
        .then((res: any) => {
          console.log(res)
          this.messageService.add({severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000});
        })
        .catch((err: any) => {
          console.log(err)
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'Product Not Created', life: 3000});
        });
    }
    this.productDialog = false;
    let productList:any = await this.productService.getAllProduct();
    this.products = productList;
  }


}
