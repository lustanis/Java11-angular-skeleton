import {ChangeDetectionStrategy, ChangeDetectorRef, Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {firstValueFrom} from 'rxjs';


@Component({
  templateUrl: './Page1.component.html',
  styleUrls: ['./Page1.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class Page1 {
  constructor(private httpClient: HttpClient, private cd: ChangeDetectorRef) {}
  dd: string = '';

  public async send() {

    this.dd = await firstValueFrom(this.httpClient.get("/api/user", {responseType: 'text'}));
    this.cd.markForCheck();
  }
}
