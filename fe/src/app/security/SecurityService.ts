import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {firstValueFrom} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {
  constructor(private httpClient: HttpClient) {
  }

  async login(username: string, password: string) {
    try {
      const response = await firstValueFrom(this.httpClient.post('/api/v1/auth/authenticate', {
        username,
        password
      }, {observe: 'response'}));
      if (response.headers.has('Authorization') && <string>response.headers.get('Authorization')) {
        localStorage.setItem('authToken', <string>response.headers.get('Authorization'));
      }
      else{
        throw new Error("No Authorization token in response!");
      }
    }
    catch(e) {
      console.error("Can not login!", e);
    }
  }

  logout(){
    localStorage.removeItem('authToken');
  }

  getToken() {
    return localStorage.getItem('authToken');
  }
}
