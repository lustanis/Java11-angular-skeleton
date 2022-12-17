import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import { EMPTY } from 'rxjs';
import {SecurityService} from './SecurityService';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class JwtInterceptor implements HttpInterceptor {
  constructor(private security: SecurityService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.security.getToken();
    if(!token){
      this.router.navigate(['login']);
      return EMPTY;
    }
    req.headers.set('Authorization', `Bearer ${token}`);
    return next.handle(req);
  }

}
