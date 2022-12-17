import { Component } from '@angular/core';
import {SecurityService} from './security/SecurityService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
   constructor(public security: SecurityService) {
   }
}
