import {Component} from '@angular/core';
import {SecurityService} from '../SecurityService';

@Component({
  templateUrl: './LoginForm.component.html',
  styleUrls: ['./LoginForm.component.css'],
  selector: 'login-form'
})
export class LoginForm {
  constructor(public securityService: SecurityService) {
  }
}
