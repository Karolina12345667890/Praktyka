import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import { NotifierService } from 'angular-notifier';
import {ActivatedRoute} from "@angular/router";
import {LoginServiceService} from "../login-service.service";
import {DatePipe} from "@angular/common";

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

  constructor(private fb: FormBuilder, private httpClient: HttpClient,notifierService: NotifierService,private activatedroute: ActivatedRoute,private authService: LoginServiceService,private datePipe: DatePipe) {
    this.notifier = notifierService;
    this.ankietaStudentForm = this.fb.group({
      groupId: "",
      studentName: new FormControl('', [Validators.required,]),
      studentSurname: new FormControl('', [Validators.required,]),
      studentSpecialization: new FormControl('', [Validators.required,]),
      instytutionType: new FormControl('', [Validators.required,]),
      companyNameAndLocation: new FormControl('', [Validators.required,]),
      studentInternshipStart: new FormControl('', [Validators.required,]),
      studentInternshipEnd: new FormControl('', [Validators.required,]),
      answerTo1: new FormControl('', [Validators.required,]),
      answerTo2: new FormControl('', [Validators.required,]),
      answerTo3: new FormControl('', [Validators.required,]),
      answerTo4: new FormControl('', [Validators.required,]),
      answerTo5: new FormControl('', [Validators.required,]),
      answerTo5atext: '.....',
      answerTo6: new FormControl('', [Validators.required,]),
      answerTo7: new FormControl('', [Validators.required,]),
      answerTo7atext: '....',
      answerTo8: new FormControl('', [Validators.required,]),
      answerTo91: new FormControl('', [Validators.required,]),
      answerTo92: new FormControl('', [Validators.required,]),
      answerTo93: new FormControl('', [Validators.required,]),
      answerTo10: new FormControl('', [Validators.required,]),
      answerTo11: new FormControl('', [Validators.required,]),
      answerTo11text: '.....',
      answerTo12: new FormControl('', [Validators.required,]),
      answerTo12atext: '....',
      answerTo12btext: '....',
      answerTo13: new FormControl('', [Validators.required,]),
      answerTo13text: '....',
      answerTo14text: '....',
    });

this.load();


  }

  load(){

    this.activatedroute.queryParams.subscribe(v => {
        this.ankietaStudentForm.get("groupId").setValue(v.id);
        this.ankietaStudentForm.get("studentSpecialization").setValue(v.s);

      this.authService.getResource('http://localhost:8080/api/document/porozumienie/'+v.id+'/data').subscribe(
        value => {
          this.ankietaStudentForm.get("companyNameAndLocation").setValue(value.companyName + ' ' + value.companyLocationCity +  ' ' + value.companyLocationStreet);
          this.ankietaStudentForm.get("studentInternshipStart").setValue(this.datePipe.transform(value.studentInternshipStart, 'yyyy-MM-dd').toString());
          this.ankietaStudentForm.get("studentInternshipEnd").setValue(this.datePipe.transform(value.studentInternshipEnd, 'yyyy-MM-dd').toString());
        },
        error => console.log(error),
      );

      }
    );

    this.authService.getResource('http://localhost:8080/api/user').subscribe(
      value => {
        this.ankietaStudentForm.get("studentName").setValue(value.name);
        this.ankietaStudentForm.get("studentSurname").setValue(value.surname);
      },
      error => console.log(error),
    );


  }

  get getstudentName() { return this.ankietaStudentForm.get('studentName')}
  get getstudentSurname() { return this.ankietaStudentForm.get('studentSurname')}
  get getstudentSpecialization() { return this.ankietaStudentForm.get('studentSpecialization')}
  get getinstytutionType() { return this.ankietaStudentForm.get('instytutionType')}
  get getcompanyNameAndLocation() { return this.ankietaStudentForm.get('companyNameAndLocation')}
  get getstudentInternshipStart() { return this.ankietaStudentForm.get('studentInternshipStart')}
  get getstudentInternshipEnd() { return this.ankietaStudentForm.get('studentInternshipEnd')}
  ngOnInit() {
  }

  onSubmit() {
    console.log(this.ankietaStudentForm.value);
    this.notifier.notify( 'success', 'Pomyślnie wysłano ankiete' );
  }

  nextQuestion() {

    if (this.questionNumber == 0 && this.ankietaStudentForm.get('studentName').value != '' && this.ankietaStudentForm.get('studentSurname').value != ''
      && this.ankietaStudentForm.get('instytutionType').value != '' && this.ankietaStudentForm.get('companyNameAndLocation').value != ''
      && this.ankietaStudentForm.get('studentInternshipStart').value != '' && this.ankietaStudentForm.get('studentInternshipEnd').value != '' && this.ankietaStudentForm.get('studentSpecialization').value != '') {
      if (this.ankietaStudentForm.get('studentInternshipStart').value < this.ankietaStudentForm.get('studentInternshipEnd').value) {
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

  peviousQuestion() {
    if (this.questionNumber > 0) {
      this.questionNumber--;
      this.notifier.notify( 'info', 'Poprzednie pytanie' );
    }
  }
}
