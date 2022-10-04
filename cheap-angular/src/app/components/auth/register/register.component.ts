import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {User} from "../../../model/user";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;
  selectedFile: any;


  constructor(private formBuilder: FormBuilder, private authService: AuthService,
              private router: Router, private messageService: MessageService) {
    this.form = formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
      mail: [null, Validators.required],
      name: [null, Validators.required],
      telNumber: [null, Validators.required],
      role: ["KULLANICI", Validators.required],
      image: [null, Validators.required]
    })
  }

  ngOnInit(): void {
  }


  change(event: any) {
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile)
  }


  async register() {
    this.form.get('image')?.reset()
    let addedUser: any;
    await this.authService.register(this.form.value).toPromise()
      .then((res: any) => {
        console.log(res);
        addedUser = res;
        this.router.navigate(["/login"]);
      })
      .catch((err: any) => {
        this.messageService.add({
          severity: 'error', summary: 'Hatalı giriş!',
          detail: err.error
        });
      });

    if (addedUser)
      await this.updateUser(addedUser)
  }

  async uploadImage(addedUser: any) {
    var uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
    // Fotoğrafı db at atılan fotoyu usera bagla
    let image:any = await this.authService.uploadImage(uploadImageData);
    console.log("IMAGE ATILDI")
    console.log(image);
    return image;
  }

 async updateUser(user:any){
  let image:any = await this.uploadImage(user)
   user.image = image
   console.log(user)
   let updatedUser:any =  await this.authService.updateUser(user);
  console.log(updatedUser)
  }

}
