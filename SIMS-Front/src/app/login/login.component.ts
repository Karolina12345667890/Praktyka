import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import { LoginServiceService } from '../login-service.service';
import {AuthGuardService} from "../auth-guard.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm;
  // wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private auth: LoginServiceService,private fb: FormBuilder,private guard: AuthGuardService) {
    // tworzy wypełniony loginform w celu łatwiejszego testowania
    this.loginForm = this.fb.group({
    login: new FormControl('admin', [ Validators.required, ]),
    password: new FormControl('admin', [ Validators.required, ]),
  }); }


  get getlogin() { return this.loginForm.get('login')}
  get getpassword() { return this.loginForm.get('password')}

  ngOnInit() { }

  // wysyła do metody login znajdyującej się w LoginServiceService login oraz hasło z strony logowania
  login(){
    this.auth.login(this.loginForm.controls.login.value,this.loginForm.controls.password.value);

  }
  // uruchamia metode logout z LoginServiceService
  logOut(){
    this.auth.logout();
  }



}
