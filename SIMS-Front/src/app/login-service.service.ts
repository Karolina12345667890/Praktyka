import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";
import {Router} from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {
  private isLogged = new Subject<boolean>();

  constructor(private router: Router) { }

  // prosta metoda do logowania przyjmująca 2 string login i password
  login(login: string, password: string) {
  // tymczasowa opcja sprawdzająca czy dane logowania są poprawne
  if(login=="admin"&& password=="admin"){
  this.isLogged.next(true);
  this.router.navigateByUrl('/home');

  }
  }

  //metoda powodująca wyglogowanie użytkownika i zabranie u dostępu do niektórych stron
  logout() {
    this.isLogged.next(false);
    this.router.navigateByUrl('/home');
  }


  // metoda zwracająca status logowania jako observable
  getLoginStatus(): Observable<Boolean> {
    return this.isLogged.asObservable();
  }

}
