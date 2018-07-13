import { Permission } from './permission';
import {Authoritation} from './authoritation';
import {Injectable} from '@angular/core';

import {Observable} from 'rxjs';
import {tap, delay} from 'rxjs/operators';
import {of} from 'rxjs/observable/of';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class AuthService {
  user: Authoritation;

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  private authURL = 'http://localhost:8080/auth';


  constructor(
    private http: HttpClient) {}

  login(user: Authoritation): Observable<Authoritation> {
    return this.http.post<Authoritation>(this.authURL, user, httpOptions);
  }
  
  getAvaliablePermissions(): Observable<Permission[]>{
    return this.http.get<Permission[]>(this.authURL+'/permissions',httpOptions);
  }
  
  
  isLoggedIn(){
    return localStorage.getItem('loggedUser')!=null;
  }
 
  logout(): void {
    localStorage.clear();
  }
}