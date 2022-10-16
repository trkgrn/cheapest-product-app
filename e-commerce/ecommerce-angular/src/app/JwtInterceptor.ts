import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpErrorResponse
} from '@angular/common/http';
import {map, Observable, tap} from 'rxjs';
import {AuthService} from "./services/auth.service";
import {Router} from "@angular/router";


@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService, private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add auth header with jwt if account is logged in and request is to the api url
    const isLoggedIn = this.authService.getToken();
    if (isLoggedIn) {
      request = request.clone({
        setHeaders: {Authorization: `Bearer ${this.authService.getToken()}`}
      });
    }

    return next.handle(request).pipe(
      tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          if (event.headers.get("refreshToken")) {
            localStorage.setItem("token", event.headers.get("refreshToken") as string);
          }
        }
        return event;
      })
    ).pipe( tap(() => {},
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status == 401) {
            this.authService.unauthorized();
          }
        }
      }));

  }
}
