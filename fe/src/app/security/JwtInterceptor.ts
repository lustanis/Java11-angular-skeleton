import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import { EMPTY } from 'rxjs';
import {SecurityService} from './SecurityService';
import {Router} from '@angular/router';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private security: SecurityService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(req.url.endsWith('/login')){
      return next.handle(req);
    }
    const token = this.security.getToken();
    if(!token){
      this.router.navigate(['login']);
      return EMPTY;
    }
    return next.handle(req.clone({headers: req.headers.set("Authorization", token)}));
  }

}
