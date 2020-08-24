import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {NotifierService} from 'angular-notifier';
import {ActivatedRoute} from "@angular/router";
import {LoginServiceService} from "../login-service.service";

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

  constructor(private fb: FormBuilder, private httpClient: HttpClient, notifierService: NotifierService, private activatedroute: ActivatedRoute, private authService: LoginServiceService) {
    this.notifier = notifierService;
    this.ankietaPracForm = this.fb.group({
      groupId: '',
      answers: this.fb.array([]),
      answerTo16text: [''],
      companyinfo: new FormControl('', [Validators.required,]),
      numberofstudents: new FormControl('', [Validators.required,]),
    });

    this.answers = this.ankietaPracForm.get('answers') as FormArray;
    for (let i: number = 0; i < 17; i++) {
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
    console.log(this.ankietaPracForm.value);
    this.notifier.notify('success', 'Pomyślnie wysłano ankiete');
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
