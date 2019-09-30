import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

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

  constructor(private fb: FormBuilder, private httpClient: HttpClient) {
    this.ankietaPracForm = this.fb.group({
      answers: this.fb.array([]),
      answerTo16text: [''],
      companyinfo: new FormControl('', [ Validators.required, ]),
      numberofstudents: new FormControl('', [ Validators.required, ]),
    });

    this.answers = this.ankietaPracForm.get('answers') as FormArray;
    for (let i: number = 0; i < 17; i++) {
      this.answers.push(new FormControl('', [Validators.required,]));
    }

  }

  ngOnInit() {
  }

  onSubmit() {
    console.log(this.ankietaPracForm);
  }

  nextQuestion() {

   console.log(this.answers.at(this.questionNumber).value);
     if ( this.answers.at(this.questionNumber).value != '') {
       this.questionNumber++;
     }
  }
  peviousQuestion(){
    if(this.questionNumber>0) {
      this.questionNumber--;
    }
  }

}
