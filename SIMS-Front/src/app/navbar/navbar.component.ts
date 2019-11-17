import {Component, OnInit} from '@angular/core';
import {LoginServiceService} from '../login-service.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {


  loggedIn: boolean;

  // wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private auth: LoginServiceService) {
    //nasłuchuje status zalogowania do zmiennej loggedIn
    this.auth.getLoginStatus().subscribe((status: boolean) => this.loggedIn = status);
  }

  ngOnInit() {
  }

  // uruchamia metode logout z LoginServiceService
  logOut() {
    this.auth.logout();
  }
}
