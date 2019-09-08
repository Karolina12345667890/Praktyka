import { Component, OnInit } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import { LoginServiceService } from '../login-service.service';
import {AuthGuardService} from "../auth-guard.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm;

  constructor(private auth: LoginServiceService,private fb: FormBuilder,private guard: AuthGuardService) {
    this.loginForm = this.fb.group({
    login: ['admin'],
    password: ['admin'],

  }); }

  ngOnInit() {

  }

  login(){
    this.auth.login(this.loginForm.controls.login.value,this.loginForm.controls.password.value);

  }

  logOut(){
    this.auth.logout();
  }



}
