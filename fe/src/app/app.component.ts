import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { SecurityService } from './security/SecurityService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(
    public translate: TranslateService,
    public security: SecurityService
  ) {
    translate.addLangs(['en', 'fr']);
    translate.setDefaultLang('en');
  }

  switchLang(lang: string) {
    this.translate.use(lang);
  }
}
