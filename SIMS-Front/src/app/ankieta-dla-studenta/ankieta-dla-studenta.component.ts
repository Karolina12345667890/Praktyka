import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-ankieta-dla-studenta',
  templateUrl: './ankieta-dla-studenta.component.html',
  styleUrls: ['./ankieta-dla-studenta.component.css']
})
export class AnkietaDlaStudentaComponent implements OnInit {

  questionNumber = 0;
  totalQuestions = 14;
  ankietaStudentForm;
  private readonly notifier: NotifierService;

  constructor(private fb: FormBuilder, private httpClient: HttpClient,notifierService: NotifierService) {
    this.notifier = notifierService;
    this.ankietaStudentForm = this.fb.group({
      studentName: new FormControl('', [ Validators.required, ]),
      studentSurname: new FormControl('', [ Validators.required, ]),
      studentSpecialization: new FormControl('', [ Validators.required, ]),
      instytutionType: new FormControl('', [ Validators.required, ]),
      companyNameAndLocation: new FormControl('', [ Validators.required, ]),
      periodFrom: new FormControl('', [ Validators.required, ]),
      periodTo: new FormControl('', [ Validators.required, ]),
      answerTo1: new FormControl('', [ Validators.required, ]),
      answerTo2: new FormControl('', [ Validators.required, ]),
      answerTo3: new FormControl('', [ Validators.required, ]),
      answerTo4: new FormControl('', [ Validators.required, ]),
      answerTo5: new FormControl('', [ Validators.required, ]),
      answerTo5atext: '.....',
      answerTo6: new FormControl('', [ Validators.required, ]),
      answerTo7: new FormControl('', [ Validators.required, ]),
      answerTo7atext: '....',
      answerTo8: new FormControl('', [ Validators.required, ]),
      answerTo91: new FormControl('', [ Validators.required, ]),
      answerTo92: new FormControl('', [ Validators.required, ]),
      answerTo93: new FormControl('', [ Validators.required, ]),
      answerTo10: new FormControl('', [ Validators.required, ]),
      answerTo11: new FormControl('', [ Validators.required, ]),
      answerTo11text: '.....',
      answerTo12: new FormControl('', [ Validators.required, ]),
      answerTo12atext: '....',
      answerTo12btext: '....',
      answerTo13: new FormControl('', [ Validators.required, ]),
      answerTo13text: '....',
      answerTo14text: '....',
    });
  }

  get getstudentName() { return this.ankietaStudentForm.get('studentName')}
  get getstudentSurname() { return this.ankietaStudentForm.get('studentSurname')}
  get getstudentSpecialization() { return this.ankietaStudentForm.get('studentSpecialization')}
  get getinstytutionType() { return this.ankietaStudentForm.get('instytutionType')}
  get getcompanyNameAndLocation() { return this.ankietaStudentForm.get('companyNameAndLocation')}
  get getperiodFrom() { return this.ankietaStudentForm.get('periodFrom')}
  get getperiodTo() { return this.ankietaStudentForm.get('periodTo')}
  ngOnInit() {
  }

  onSubmit() {
    console.log(this.ankietaStudentForm);
    this.notifier.notify( 'success', 'Pomyślnie wysłano ankiete' );
  }

  nextQuestion() {

    if (this.questionNumber == 0 && this.ankietaStudentForm.get("studentName").value != '' && this.ankietaStudentForm.get("studentSurname").value != ''
      && this.ankietaStudentForm.get("instytutionType").value != '' && this.ankietaStudentForm.get("companyNameAndLocation").value != ''
      && this.ankietaStudentForm.get("periodFrom").value != '' && this.ankietaStudentForm.get("periodTo").value != '' && this.ankietaStudentForm.get("studentSpecialization").value != '') {
      if (this.ankietaStudentForm.get("periodFrom").value < this.ankietaStudentForm.get("periodTo").value) {
        this.questionNumber++;
        this.notifier.notify( 'info', 'Następne pytanie' );
      }
    }else if (this.questionNumber == 9 && this.ankietaStudentForm.get("answerTo91").value != '' && this.ankietaStudentForm.get("answerTo92").value != '' && this.ankietaStudentForm.get("answerTo93").value != '') {
      this.questionNumber++;
      this.notifier.notify( 'info', 'Następne pytanie' );
    }else if (this.questionNumber != 0 && this.questionNumber != 9 && this.ankietaStudentForm.get("answerTo" + this.questionNumber).value != '') {
      this.questionNumber++;
      this.notifier.notify( 'info', 'Następne pytanie' );
    }else{
      this.notifier.notify( 'warning', 'Wypełnij wymagane pola' );
    }


  }
  peviousQuestion(){
    if(this.questionNumber>0) {
      this.questionNumber--;
      this.notifier.notify( 'info', 'Poprzednie pytanie' );
    }
  }
}
