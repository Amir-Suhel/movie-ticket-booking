import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAuthService } from './user-auth.service';

const headers = new HttpHeaders().append( 'responseType', 'text' )
  .append('No-Auth', 'True');

@Injectable({
  providedIn: 'root'
})
export class UserService {

  PATH_OF_API_signup = "http://localhost:8080/api/auth";
  PATH_OF_API = "http://localhost:9090/call/consumer";
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  // httpOptions = {
  //   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  // };

  requestHeader = new HttpHeaders(
    { "No-Auth": "True" });

  constructor(private httpClient: HttpClient, private userAuthService: UserAuthService) { }

  // login api
  public login(loginData: any) {
    return this.httpClient.post(this.PATH_OF_API + "/login", loginData, this.httpOptions);
  };

  public forUsers() {
    return this.httpClient.get('http://localhost:8080/api/test/user', { responseType: 'text' });
  }

  public forAdmin() {
    return this.httpClient.get('http://localhost:8080/api/test/admin', { responseType: 'text' });
  }

  public roleMatch(allowedRoles: any): boolean {
    let isMatch = false;
    const userRoles = this.userAuthService.getRoles();
    if (userRoles != null && userRoles) {
      for (let i = 0; i < userRoles.length; i++) {
        for (let j = 0; j < allowedRoles.length; j++) {
          if (userRoles[i] == allowedRoles[j]) {
            isMatch = true;
            return isMatch;
          }
          return isMatch;
        }
      }
    }
    return isMatch;
  }


  //sign up api
  public signup(signupData: any) {
    return this.httpClient.post<any>(this.PATH_OF_API_signup + '/signUp', signupData, this.httpOptions);
  }

  //forgot password
  public forgotPassword(passwordForm: any) {
    return this.httpClient.put(this.PATH_OF_API_signup + '/forgotPassword', passwordForm, this.httpOptions);
  }

}
