import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import {AuthService} from "../../../services/auth.service";


@Injectable()
export class LoginGuard implements CanActivate {
  constructor(private authService:AuthService, private router:Router){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    let logged = this.authService.isUserSignedin()
    if(logged)
    {
      const role = route.data['roles'] as Array<string>;
      if(role){
        const match = this.authService.roleMatch(role);
        if (match) {
          return true;
        } else {
          this.router.navigate(["/forbidden"]);
          return false;
        }
      }

    }
    this.router.navigate(["/login"]);
    return false;
  }
}
