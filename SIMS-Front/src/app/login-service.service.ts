import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {Router} from '@angular/router';
import {NotifierService} from 'angular-notifier';
import {OAuthService} from 'angular-oauth2-oidc';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';
import * as jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {
  private isLogged = new Subject<boolean>();
  private readonly notifier: NotifierService;

  constructor(private router: Router, private oauthService: OAuthService, private http: HttpClient, private cookieService: CookieService, private notifierService: NotifierService) {
    this.oauthService.configure({
      loginUrl: 'http://localhost:8080/oauth/authorize',
      logoutUrl: 'http://localhost:8080/logout',
      redirectUri: 'http://localhost:4200/',
      clientId: 'sampleClientId',
      scope: 'read write foo bar',
      disablePKCE: true,
      oidc: false
    });
    this.oauthService.setStorage(sessionStorage);
    this.oauthService.tryLogin({});
    this.notifier = notifierService;
  }

  obtainAccessToken() {
    this.oauthService.initImplicitFlow();
  }


  getResource(resourceUrl: string, httpHeader = new HttpHeaders()): Observable<any> {
    let newHeaders = httpHeader;
    newHeaders.append('Content-type', 'application/x-www-form-urlencoded; charset=utf-8');
    if (this.isLoggedIn()) {
      newHeaders = newHeaders.append('Authorization', 'Bearer ' + this.oauthService.getAccessToken());
    }

    return this.http.get(resourceUrl, {headers: newHeaders});
    // return this.http.get(resourceUrl);
  }

  deleteResource(resourceUrl: string, httpHeader = new HttpHeaders()): Observable<any> {
    const newHeaders = httpHeader
      .append('Content-type', 'application/x-www-form-urlencoded; charset=utf-8')
      .append('Authorization', 'Bearer ' + this.oauthService.getAccessToken());

    return this.http.delete(resourceUrl, {headers: newHeaders});
  }

  postResource(resourceUrl: string, body: any, httpHeader = new HttpHeaders()): Observable<any> {
    console.log('1234');
    const newHeaders = httpHeader
      .append('Content-type', 'application/json')
      .append('Accept', 'application/json')
      .append('Authorization', 'Bearer ' + this.oauthService.getAccessToken());
    return this.http.post(resourceUrl, body, {headers: newHeaders});
  }

  putResource(resourceUrl: string, body: any, httpHeader = new HttpHeaders()): Observable<any> {
    const newHeaders = httpHeader
      .append('Content-type', 'application/x-www-form-urlencoded; charset=utf-8')
      .append('Authorization', 'Bearer ' + this.oauthService.getAccessToken());

    return this.http.put(resourceUrl, body, {headers: newHeaders});
  }


  isLoggedIn() {
    // console.log(this.oauthService.getAccessToken());
    if (this.oauthService.getAccessToken() === null) {
      this.isLogged.next(false);
      return false;
    }

    this.isLogged.next(true);
    return true;
  }

  logout() {
    this.isLoggedIn();
    this.oauthService.logOut();
    window.open('http://localhost:8080/logout', 'theFrame');
    // .addEventListener('change', function() { window.close(); } , false);
    // this.getResource('http://localhost:8080/logout').subscribe(value => {
    //   // this.deleteResource('http://localhost:8080/oauth/token');
    //   location.reload();
    // });
  }

  // prosta metoda do logowania przyjmująca 2 string login i password
  // login(login: string, password: string) {
  // tymczasowa opcja sprawdzająca czy dane logowania są poprawne
  //if (login == 'admin' && password == 'admin') {
  //     this.isLogged.next(true);
  //  this.router.navigateByUrl('/home');

  //  }
  // }

  // //metoda powodująca wyglogowanie użytkownika i zabranie u dostępu do niektórych stron
  // logout() {
  //   this.isLogged.next(false);
  //   this.router.navigateByUrl('/home');
  // }


  // metoda zwracająca status logowania jako observable
  getLoginStatus(): Observable<Boolean> {
    return this.isLogged.asObservable();
  }

  getResource2(resourceUrl: string, httpHeader = new HttpHeaders()): Observable<any> {
    let newHeaders = httpHeader;
    newHeaders.append('Content-type', 'application/x-www-form-urlencoded; charset=utf-8');
    if (this.isLoggedIn()) {
      newHeaders = newHeaders.append('Authorization', 'Bearer ' + this.oauthService.getAccessToken());
    }

    return this.http.get(resourceUrl, {headers: newHeaders, responseType: 'blob'});
    // return this.http.get(resourceUrl);
  }

  getRoles() {
    if (this.oauthService.getAccessToken() !== null) {
      const decodedToken = jwt_decode(this.oauthService.getAccessToken());
      const roles = decodedToken.roles;
      return roles;
    }
    else {
      return null;
    }
  }

  isAdmin() {
    if (this.oauthService.getAccessToken() !== null) {
      let tmp: string = this.getRoles();
      if (tmp.includes("ROLE_ADMIN")) return true;
      else return false;
    }
    else {
      return false;
    }
  }


  isGroupAdmin() {
    if (this.oauthService.getAccessToken() !== null) {
      let tmp: string = this.getRoles();
      if (tmp.includes("ROLE_GROUP_ADMIN")) return true;
      else return false;
    }
    else {
      return false;
    }
  }

}
