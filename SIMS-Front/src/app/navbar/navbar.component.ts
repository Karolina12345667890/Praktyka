import {Component, OnInit} from '@angular/core';
import {LoginServiceService} from '../login-service.service';
import {FormBuilder} from "@angular/forms";
import {AuthGuardService} from "../auth-guard.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {


  loggedIn: boolean;
  isAdmin:boolean =false;


  // wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private auth: LoginServiceService) {
    //nasłuchuje status zalogowania do zmiennej loggedIn
    this.auth.getLoginStatus().subscribe((status: boolean) => this.loggedIn = status);
  }

  ngOnInit() {
    this.isAdmin = this.auth.isAdmin();
  }

  login() {
    this.auth.obtainAccessToken();
  }
  // uruchamia metode logout z LoginServiceService
  logout() {
    this.auth.logout();
  }
}
