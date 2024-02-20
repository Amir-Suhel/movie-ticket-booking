import { Injectable } from '@angular/core';
import { LoginResponse } from '../model/loginresponse';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {

  constructor() { }

  public setRoles(roles: []) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRoles(): [] {
    return JSON.parse(localStorage.getItem('roles') || 'null');
  }

  public setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
  }

  public getToken(): string {
    return localStorage.getItem('jwtToken') || 'null';
  }

  public getCurrentUser() {
    return JSON.parse(localStorage.getItem('currentUser') || '{}');
  }

  public setCurrentUser(value: LoginResponse) {
    localStorage.setItem('currentUser', JSON.stringify(value));
  }

  public clear() {
    localStorage.clear();
  }

  public isLoogedIn() {
    return this.getRoles() && this.getToken();
  }

  public isAdmin() {
    const roles: any[] = this.getRoles();
    //console.log(roles);
    return roles[0] === 'ROLE_ADMIN';
  }

  public isUser() {
    const roles: any[] = this.getRoles();
    //console.log(roles);
    return roles[0] === 'ROLE_USER';
  }

}
