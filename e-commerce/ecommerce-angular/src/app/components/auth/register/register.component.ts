import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css','./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;
  selectedFile: any;
  roles:any[] = ['KULLANICI','ADMIN'];


  constructor(private formBuilder: FormBuilder, private authService: AuthService,
              private router: Router, private messageService: MessageService) {
    this.form = formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
      mail: [null, Validators.required],
      name: [null, Validators.required],
      telNumber: [null, Validators.required],
      role: [null, Validators.required]
    })
  }

  ngOnInit(): void {
  }


  change(event: any) {
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile)
  }


  async register() {
    await this.authService.register(this.form.value).toPromise()
      .then((res: any) => {
        console.log(res);
        this.router.navigate(["/login"]);
      })
      .catch((err: any) => {
        this.messageService.add({
          severity: 'error', summary: 'Hatalı giriş!',
          detail: err.error
        });
      });
  }

 async updateUser(user:any){
   console.log(user)
   let updatedUser:any =  await this.authService.updateUser(user);
  console.log(updatedUser)
  }

}
