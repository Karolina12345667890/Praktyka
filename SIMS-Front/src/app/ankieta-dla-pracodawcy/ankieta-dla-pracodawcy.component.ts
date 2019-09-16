import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
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

  constructor(private fb: FormBuilder, private httpClient: HttpClient) {
    this.ankietaPracForm = this.fb.group({
      answerTo0: new FormControl('', [ Validators.required, ]),
      answerTo1: new FormControl('', [ Validators.required, ]),
      answerTo2: new FormControl('', [ Validators.required, ]),
      answerTo3: new FormControl('', [ Validators.required, ]),
      answerTo4: new FormControl('', [ Validators.required, ]),
      answerTo5: new FormControl('', [ Validators.required, ]),
      answerTo6: new FormControl('', [ Validators.required, ]),
      answerTo7: new FormControl('', [ Validators.required, ]),
      answerTo8: new FormControl('', [ Validators.required, ]),
      answerTo9: new FormControl('', [ Validators.required, ]),
      answerTo10: new FormControl('', [ Validators.required, ]),
      answerTo11: new FormControl('', [ Validators.required, ]),
      answerTo12: new FormControl('', [ Validators.required, ]),
      answerTo13: new FormControl('', [ Validators.required, ]),
      answerTo14: new FormControl('', [ Validators.required, ]),
      answerTo15: new FormControl('', [ Validators.required, ]),
      answerTo15text: [''],
      answerTo16text: [''],
      companyinfo: new FormControl('', [ Validators.required, ]),
      numberofstudents: new FormControl('', [ Validators.required, ]),
    });

  }


  ngOnInit() {
  }

  onSubmit() {
    console.log(this.ankietaPracForm);
  }

  nextQuestion() {

    if (this.ankietaPracForm.get("answerTo" + this.questionNumber).value != '') {
      this.questionNumber++;
    }
  }
  peviousQuestion(){
    if(this.questionNumber>0) {
      this.questionNumber--;
    }
  }

}
