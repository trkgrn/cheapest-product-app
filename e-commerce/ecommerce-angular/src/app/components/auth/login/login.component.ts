import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form:FormGroup;

  constructor(private formBuilder:FormBuilder, private authService:AuthService,
              private router:Router, private messageService:MessageService) {
    this.form = formBuilder.group({
      username:[null , Validators.required],
      password:[null, Validators.required]
    })
  }

  ngOnInit(): void {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
  }

 async login()
  {
    let resp:any = await this.authService.login(this.form.value).toPromise()
      .then(r=>{
        if(this.authService.getRole() == 'ADMIN')
          this.router.navigate(["/product/manage"])
        else
          this.router.navigate(["/products"])
      })
      .catch((err:any)=>{
        this.messageService.add({severity: 'error', summary: 'Giriş Başarısız',
          detail: 'Hatalı giriş. Lütfen bilgilerini kontrol edip tekrar deneyin.'});
      });
  }
}
