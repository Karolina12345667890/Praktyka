import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {LoginServiceService} from "./login-service.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  private loggedIn : boolean = false;

  constructor(private router: Router, private auth : LoginServiceService) {
    this.auth.getLoginStatis().subscribe((flag : boolean) => {
      this.loggedIn=flag;
    })
  }

  canActivate(route : ActivatedRouteSnapshot, state : RouterStateSnapshot){
    return this.loggedIn;
  }



}

