import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";
import {Router} from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {
  private isLogged = new Subject<boolean>();

  constructor(private router: Router) { }

  login(login: string, password: string) {

  if(login=="admin"&& password=="admin"){
  this.isLogged.next(true);
  this.router.navigateByUrl('/home');

  }
  }

  logout() {
    this.isLogged.next(false);
    this.router.navigateByUrl('/home');
  }

  getLoginStatis(): Observable<Boolean> {
    return this.isLogged.asObservable();


  }

}
