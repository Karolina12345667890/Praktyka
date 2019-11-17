import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {LoginServiceService} from './login-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  private loggedIn: boolean = false;

  // wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private router: Router, private auth: LoginServiceService) {
    //pobiera z LoginServiceService informacje o tym czy użytkownik jest zalogowany, ostrzymuje informacjie z strumienia observable(nasłuchuje go)
    this.auth.getLoginStatus().subscribe((flag: boolean) => {
      this.loggedIn = flag;
    });
  }

  // metoda wywoływana gdy chcemy się dostać na jakąś strone zwracające informacje o tym czy mamy do niej dostęp czy nie
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.loggedIn;
  }


}

