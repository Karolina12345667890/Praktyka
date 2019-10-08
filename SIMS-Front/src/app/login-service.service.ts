import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";
import {Router} from '@angular/router';
import { NotifierService } from 'angular-notifier';
@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {
  private isLogged = new Subject<boolean>();
  private readonly notifier: NotifierService;

  constructor(private router: Router, notifierService: NotifierService ) {
    this.notifier = notifierService;
  }

  // prosta metoda do logowania przyjmująca 2 string login i password
  login(login: string, password: string) {
  // tymczasowa opcja sprawdzająca czy dane logowania są poprawne
  if(login=="admin"&& password=="admin"){
  this.isLogged.next(true);
  this.router.navigateByUrl('/home');
    this.notifier.notify( 'success', 'Pomyślnie zalogowano' );
  }else{
    this.notifier.notify( 'error', 'Zły login lub hasło' );
  }
  }

  //metoda powodująca wyglogowanie użytkownika i zabranie u dostępu do niektórych stron
  logout() {
    this.isLogged.next(false);
    this.notifier.notify( 'success', 'Pomyślnie wylogowano' );
    this.router.navigateByUrl('/home');
  }


  // metoda zwracająca status logowania jako observable
  getLoginStatus(): Observable<Boolean> {
    return this.isLogged.asObservable();
  }

}
