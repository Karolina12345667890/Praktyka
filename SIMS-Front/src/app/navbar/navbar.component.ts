import { Component, OnInit } from '@angular/core';
import { LoginServiceService } from '../login-service.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  loggedIn : boolean;
  constructor(private auth : LoginServiceService) {
    this.auth.getLoginStatis().subscribe((status : boolean) =>this.loggedIn=status)
  }

  ngOnInit() {
  }
  logOut(){
    this.auth.logout();
  }
}
