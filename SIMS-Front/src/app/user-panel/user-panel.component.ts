import { Component, OnInit } from '@angular/core';
import {LoginServiceService} from '../login-service.service';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {NotifierService} from "angular-notifier";
import * as bcrypt from 'bcryptjs';

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.css']
})
export class UserPanelComponent implements OnInit {

  changeFirstnameForm: FormGroup;
  changeLastnameForm: FormGroup;
  changeEmailForm: FormGroup;
  changeAlbumForm: FormGroup;
  changePasswordForm: FormGroup;

  private readonly notifier: NotifierService;
  isAdmin = false;

  firstname: string;
  lastname: string;
  album: number;
  email: string;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: LoginServiceService,
    notifierService: NotifierService
  ) {
    this.notifier = notifierService;
  }


  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
    this.load();
  }


  private load() {
    this.authService.getResource('http://localhost:8080/api/user').subscribe(
      value => {
        this.firstname = value.name;
        this.lastname = value.surname;
        this.album = value.album;
        this.email = value.email;
      },
      error => console.log(error)
    );

    this.changeFirstnameForm = this.fb.group({
      studentFirstname: new FormControl('', [Validators.required, Validators.maxLength(30)])
    })

    this.changeLastnameForm = this.fb.group({
      studentLastname: new FormControl('', [Validators.required, Validators.maxLength(30)])
    })

    this.changeEmailForm = this.fb.group({
      studentEmail: new FormControl('', [Validators.required, Validators.email])
    })

    this.changeAlbumForm = this.fb.group({
      studentAlbum: new FormControl(null, Validators.required)
    })

    this.changePasswordForm = this.fb.group({
      studentNewPassword: new FormControl('', Validators.required),
      studentNewPasswordRepeat: new FormControl('', Validators.required)
    })
  }


  showChangePasswordForm() {
    const changePassForm = document.getElementById('changePasswordForm');

    if (changePassForm.style.display == 'none') {
      changePassForm.removeAttribute('style');
    }
    else {
      changePassForm.style.display = 'none';
    }
  }


  onSubmitChangeFirstname() {
    this.authService.postResource('http://localhost:8080/api/user/edit/name', {
      name: this.changeFirstnameForm.value.studentFirstname
    }).subscribe(
      value => {
        this.load();
        this.notifier.notify("success","Pomyślnie zmieniono imię!");
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", "Wystąpił błąd podczas zmiany imienia!");
      }
    );
  }


  onSubmitChangeLastname() {
    this.authService.postResource('http://localhost:8080/api/user/edit/surname', {
      surname: this.changeLastnameForm.value.studentLastname
    }).subscribe(
      value => {
        this.load();
        this.notifier.notify("success","Pomyślnie zmieniono nazwisko!");
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", "Wystąpił błąd podczas zmiany nazwiska!");
      }
    );
  }


  onSubmitChangeEmail() {
    this.authService.postResource('http://localhost:8080/api/user/edit/email', {
      email: this.changeEmailForm.value.studentEmail
    }).subscribe(
      value => {
        this.load();
        this.notifier.notify("success","Pomyślnie zmieniono adres e-mail!");
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", "Wystąpił błąd podczas zmiany adresu e-mail!");
      }
    );
  }


  onSubmitChangeAlbum() {
    this.authService.postResource('http://localhost:8080/api/user/edit/album', {
      album: this.changeAlbumForm.value.studentAlbum
    }).subscribe(
      value => {
        this.load();
        this.notifier.notify("success","Pomyślnie zmieniono numer albumu!");
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", "Wystąpił błąd podczas zmiany numeru albumu!");
      }
    );
  }


  onSubmitChangePassword() {
    const firstInput = this.changePasswordForm.value.studentNewPassword;
    const secondInput = this.changePasswordForm.value.studentNewPasswordRepeat;

    if (firstInput === secondInput) {
      const salt = bcrypt.genSaltSync(10);
      const hashedPassword = bcrypt.hashSync(firstInput, salt);

      this.authService.postResource('http://localhost:8080/api/user/edit/pass', {
        pass: hashedPassword
      }).subscribe(
        value => {
          this.load();
          this.notifier.notify("success","Pomyślnie zmieniono hasło!");
        },
        error =>{
          console.log(error)
          this.notifier.notify("error", "Wystąpił inny błąd podczas zmiany hasła!");
        }
      );
    }
    else {
      this.notifier.notify("error", "Podane hasła nie są takie same!");
    }
  }

}
