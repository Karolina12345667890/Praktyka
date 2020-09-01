import {AfterViewInit, Component, OnInit} from '@angular/core';
import {LoginServiceService} from '../login-service.service';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, AfterViewInit {

  loggedIn: boolean;
  isAdmin: boolean = false;
  firstname: string;

  // wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private auth: LoginServiceService) {
    //nasłuchuje status zalogowania do zmiennej loggedIn
    this.auth.getLoginStatus().subscribe((status: boolean) => this.loggedIn = status);
  }


  ngOnInit() {
    setTimeout(() => { this.isAdmin = this.auth.isAdmin(); }, 100);
  }


  ngAfterViewInit(): void {
    setTimeout(() => {
      if (this.loggedIn) {
        this.auth.getResource('http://localhost:8080/api/user').subscribe(
          (value) => {
            this.firstname = value.name;
          },
          (error) => {
            console.log(error);
          }
        );
      }
    }, 500);
  }

  login() {
    this.auth.obtainAccessToken();
  }

  // uruchamia metode logout z LoginServiceService
  logout() {
    this.auth.logout();
  }
}
