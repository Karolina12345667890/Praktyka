import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {NotifierService} from 'angular-notifier';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginServiceService} from "../login-service.service";
import {ankietaPracownikDto} from "../models/ankietaPracownikDto";
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-ankieta-dla-pracodawcy',
  templateUrl: './ankieta-dla-pracodawcy.component.html',
  styleUrls: ['./ankieta-dla-pracodawcy.component.css']
})
export class AnkietaDlaPracodawcyComponent implements OnInit {

  questionNumber = 0;
  totalQuestions = 16;
  ankietaPracForm: FormGroup;
  answers: FormArray;
  private readonly notifier: NotifierService;

  constructor(private fb: FormBuilder, private httpClient: HttpClient, notifierService: NotifierService, private activatedroute: ActivatedRoute, private authService: LoginServiceService, private router: Router) {
    this.notifier = notifierService;
    this.ankietaPracForm = this.fb.group({
      groupId: '',
      answers: this.fb.array([]),
      answerTo16text: [''],
      answerTo15text: [''],
      companyinfo: new FormControl('', [Validators.required,]),
      numberofstudents: new FormControl('', [Validators.required,]),
    });

    this.answers = this.ankietaPracForm.get('answers') as FormArray;
    for (let i: number = 0; i < 16; i++) {
      this.answers.push(new FormControl('', [Validators.required,]));
    }

    this.load();
  }

  ngOnInit() {
  }

  load() {
    this.activatedroute.queryParams.subscribe(v => {
      this.ankietaPracForm.get("groupId").setValue(v.id);

      this.authService.getResource('http://localhost:8080/api/document/porozumienie/' + v.id + '/data').subscribe(
        value => {
          this.ankietaPracForm.get("companyinfo").setValue(value.companyName + ' ' + value.companyLocationCity + ' ' + value.companyLocationStreet);
        },
        error => console.log(error),
      );
    });
  }

  onSubmit() {

    let body: ankietaPracownikDto = {
      answerTo15text: this.ankietaPracForm.value.answerTo15text,
      answerTo16text: this.ankietaPracForm.value.answerTo16text,
      answer: this.ankietaPracForm.value.answers,
      companyInfo: this.ankietaPracForm.value.companyinfo,
      numberOfStudent: this.ankietaPracForm.value.numberofstudents
    };

    this.authService.postResource('http://localhost:8080/api/document/ankieta_pracownik/' + this.ankietaPracForm.value.groupId , body).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie wysłano dokument ankieta pracownik",)
         this.router.navigate(["/home"]);
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );



  }

  nextQuestion() {
    if (this.answers.at(this.questionNumber).value != '') {
      this.questionNumber++;
      this.notifier.notify('info', 'Następne pytanie');
    } else {
      this.notifier.notify('warning', 'Zaznacz jedną z odpowiedzi');
    }
  }

  peviousQuestion() {
    if (this.questionNumber > 0) {
      this.questionNumber--;
      this.notifier.notify('info', 'Poprzednie pytanie');
    }
  }

}
