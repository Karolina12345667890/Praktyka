import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import { NotifierService } from 'angular-notifier';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginServiceService} from "../login-service.service";
import {DatePipe} from "@angular/common";
import {ankietaStudentaDto} from "../models/ankietaStudentaDto";
import {MatDialog} from '@angular/material';

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
  isAdmin: boolean = false;
  groupId: number = null;

  constructor(
    private fb: FormBuilder,
    private httpClient: HttpClient,
    private notifierService: NotifierService,
    private activatedroute: ActivatedRoute,
    private authService: LoginServiceService,
    private datePipe: DatePipe,
    private router: Router
  ) {
    this.notifier = notifierService;

    this.isAdmin = this.authService.isAdmin();

    this.ankietaStudentForm = this.fb.group({
        groupId: "",
        studentName: new FormControl('', [Validators.required]),
        studentSurname: new FormControl('', [Validators.required]),
        studentSpecialization: new FormControl('', [Validators.required]),
        instytutionType: new FormControl('', [Validators.required]),
        companyNameAndLocation: new FormControl('', [Validators.required]),
        studentInternshipStart: new FormControl('', [Validators.required]),
        studentInternshipEnd: new FormControl('', [Validators.required]),
        answerTo1: new FormControl('', [Validators.required]),
        answerTo2: new FormControl('', [Validators.required]),
        answerTo3: new FormControl('', [Validators.required]),
        answerTo4: new FormControl('', [Validators.required]),
        answerTo5: new FormControl('', [Validators.required]),
        answerTo5atext: '.....',
        answerTo6: new FormControl('', [Validators.required]),
        answerTo7: new FormControl('', [Validators.required]),
        answerTo7atext: '....',
        answerTo8: new FormControl('', [Validators.required]),
        answerTo91: new FormControl('', [Validators.required]),
        answerTo92: new FormControl('', [Validators.required]),
        answerTo93: new FormControl('', [Validators.required]),
        answerTo10: new FormControl('', [Validators.required]),
        answerTo11: new FormControl('', [Validators.required]),
        answerTo11text: '.....',
        answerTo12: new FormControl('', [Validators.required]),
        answerTo12atext: '....',
        answerTo12btext: '....',
        answerTo13: new FormControl('', [Validators.required]),
        answerTo13text: '....',
        answerTo14text: '....'
    });

    this.load();
  }

  private load() {
    if (!this.isAdmin) {
      this.activatedroute.queryParams.subscribe(v => {
          this.ankietaStudentForm.get("groupId").setValue(v.id);
          this.ankietaStudentForm.get("studentSpecialization").setValue(v.s);

          this.authService.getResource('http://localhost:8080/api/document/porozumienie/' + v.id + '/data').subscribe(
            value => {
              this.ankietaStudentForm.get("companyNameAndLocation").setValue(value.companyName + ' ' + value.companyLocationCity + ' ' + value.companyLocationStreet);
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
    else {
      this.activatedroute.queryParams.subscribe(v => {
        this.authService.getResource(`http://localhost:8080/api/document/ankieta_studenta/${v.id}`).subscribe(completedSurvey => {
          this.groupId = completedSurvey.groupId;
          this.ankietaStudentForm.get('studentName').setValue(completedSurvey.studentName);
          this.ankietaStudentForm.get('studentSurname').setValue(completedSurvey.studentSurname);
          this.ankietaStudentForm.get('studentSpecialization').setValue(completedSurvey.studentSpecialization);
          this.ankietaStudentForm.get('instytutionType').setValue(completedSurvey.instytutionType);
          this.ankietaStudentForm.get('companyNameAndLocation').setValue(completedSurvey.companyNameAndLocation);
          this.ankietaStudentForm.get('studentInternshipStart').setValue(this.datePipe.transform(completedSurvey.studentInternshipStart, 'yyyy-MM-dd').toString());
          this.ankietaStudentForm.get('studentInternshipEnd').setValue(this.datePipe.transform(completedSurvey.studentInternshipEnd, 'yyyy-MM-dd').toString());
          this.ankietaStudentForm.get('answerTo1').setValue(completedSurvey.answerTo1);
          this.ankietaStudentForm.get('answerTo2').setValue(completedSurvey.answerTo2);
          this.ankietaStudentForm.get('answerTo3').setValue(completedSurvey.answerTo3);
          this.ankietaStudentForm.get('answerTo4').setValue(completedSurvey.answerTo4);
          this.ankietaStudentForm.get('answerTo5').setValue(completedSurvey.answerTo5);
          this.ankietaStudentForm.get('answerTo5atext').setValue(completedSurvey.answerTo5atext);
          this.ankietaStudentForm.get('answerTo6').setValue(completedSurvey.answerTo6);
          this.ankietaStudentForm.get('answerTo7').setValue(completedSurvey.answerTo7);
          this.ankietaStudentForm.get('answerTo7atext').setValue(completedSurvey.answerTo7atext);
          this.ankietaStudentForm.get('answerTo8').setValue(completedSurvey.answerTo8);
          this.ankietaStudentForm.get('answerTo91').setValue(completedSurvey.answerTo91);
          this.ankietaStudentForm.get('answerTo92').setValue(completedSurvey.answerTo92);
          this.ankietaStudentForm.get('answerTo93').setValue(completedSurvey.answerTo93);
          this.ankietaStudentForm.get('answerTo10').setValue(completedSurvey.answerTo10);
          this.ankietaStudentForm.get('answerTo11').setValue(completedSurvey.answerTo11);
          this.ankietaStudentForm.get('answerTo11text').setValue(completedSurvey.answerTo11text);
          this.ankietaStudentForm.get('answerTo12').setValue(completedSurvey.answerTo12);
          this.ankietaStudentForm.get('answerTo12atext').setValue(completedSurvey.answerTo12atext);
          this.ankietaStudentForm.get('answerTo12btext').setValue(completedSurvey.answerTo12btext);
          this.ankietaStudentForm.get('answerTo13').setValue(completedSurvey.answerTo13);
          this.ankietaStudentForm.get('answerTo13text').setValue(completedSurvey.answerTo13text);
          this.ankietaStudentForm.get('answerTo14text').setValue(completedSurvey.answerTo14text);

          this.ankietaStudentForm.controls['studentName'].disable();
          this.ankietaStudentForm.controls['studentSurname'].disable();
          this.ankietaStudentForm.controls['studentSpecialization'].disable();
          this.ankietaStudentForm.controls['instytutionType'].disable();
          this.ankietaStudentForm.controls['companyNameAndLocation'].disable();
          this.ankietaStudentForm.controls['studentInternshipStart'].disable();
          this.ankietaStudentForm.controls['studentInternshipEnd'].disable();
          this.ankietaStudentForm.controls['answerTo1'].disable();
          this.ankietaStudentForm.controls['answerTo2'].disable();
          this.ankietaStudentForm.controls['answerTo3'].disable();
          this.ankietaStudentForm.controls['answerTo4'].disable();
          this.ankietaStudentForm.controls['answerTo5'].disable();
          this.ankietaStudentForm.controls['answerTo5atext'].disable();
          this.ankietaStudentForm.controls['answerTo6'].disable();
          this.ankietaStudentForm.controls['answerTo7'].disable();
          this.ankietaStudentForm.controls['answerTo7atext'].disable();
          this.ankietaStudentForm.controls['answerTo8'].disable();
          this.ankietaStudentForm.controls['answerTo91'].disable();
          this.ankietaStudentForm.controls['answerTo92'].disable();
          this.ankietaStudentForm.controls['answerTo93'].disable();
          this.ankietaStudentForm.controls['answerTo10'].disable();
          this.ankietaStudentForm.controls['answerTo11'].disable();
          this.ankietaStudentForm.controls['answerTo11text'].disable();
          this.ankietaStudentForm.controls['answerTo12'].disable();
          this.ankietaStudentForm.controls['answerTo12atext'].disable();
          this.ankietaStudentForm.controls['answerTo12btext'].disable();
          this.ankietaStudentForm.controls['answerTo13'].disable();
          this.ankietaStudentForm.controls['answerTo13text'].disable();
          this.ankietaStudentForm.controls['answerTo14text'].disable();
        });
      });
    }
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

    let body: ankietaStudentaDto = {
      studentName: this.ankietaStudentForm.value.studentName,
      studentSurname: this.ankietaStudentForm.value.studentSurname,
      studentSpecialization: this.ankietaStudentForm.value.studentSpecialization,
      instytutionType: this.ankietaStudentForm.value.instytutionType,
      companyNameAndLocation: this.ankietaStudentForm.value.companyNameAndLocation,
      studentInternshipStart: this.ankietaStudentForm.value.studentInternshipStart,
      studentInternshipEnd: this.ankietaStudentForm.value.studentInternshipEnd,
      answerTo1: this.ankietaStudentForm.value.answerTo1,
      answerTo2: this.ankietaStudentForm.value.answerTo2,
      answerTo3: this.ankietaStudentForm.value.answerTo3,
      answerTo4: this.ankietaStudentForm.value.answerTo4,
      answerTo5: this.ankietaStudentForm.value.answerTo5,
      answerTo5atext: this.ankietaStudentForm.value.answerTo5atext,
      answerTo6: this.ankietaStudentForm.value.answerTo6,
      answerTo7: this.ankietaStudentForm.value.answerTo7,
      answerTo7atext: this.ankietaStudentForm.value.answerTo7atext,
      answerTo8: this.ankietaStudentForm.value.answerTo8,
      answerTo91: this.ankietaStudentForm.value.answerTo91,
      answerTo92: this.ankietaStudentForm.value.answerTo92,
      answerTo93: this.ankietaStudentForm.value.answerTo93,
      answerTo10: this.ankietaStudentForm.value.answerTo10,
      answerTo11: this.ankietaStudentForm.value.answerTo11,
      answerTo11text: this.ankietaStudentForm.value.answerTo11text,
      answerTo12: this.ankietaStudentForm.value.answerTo12,
      answerTo12atext: this.ankietaStudentForm.value.answerTo12atext,
      answerTo12btext: this.ankietaStudentForm.value.answerTo12btext,
      answerTo13: this.ankietaStudentForm.value.answerTo13,
      answerTo13text: this.ankietaStudentForm.value.answerTo13text,
      answerTo14text: this.ankietaStudentForm.value.answerTo14text

    };

    this.authService.postResource('http://localhost:8080/api/document/ankieta_studenta/' + this.ankietaStudentForm.value.groupId , body).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie wysłano dokument ankieta studenta",)
        this.router.navigate(["/home"]);
      },
      error =>{
        console.log(error);
        this.notifier.notify('error', error.error);
      }
    );



  }

  nextQuestion() {
    if (this.questionNumber == 0 && this.ankietaStudentForm.get('studentName').value != '' && this.ankietaStudentForm.get('studentSurname').value != ''
      && this.ankietaStudentForm.get('instytutionType').value != '' && this.ankietaStudentForm.get('companyNameAndLocation').value != ''
      && this.ankietaStudentForm.get('studentInternshipStart').value != '' && this.ankietaStudentForm.get('studentInternshipEnd').value != '' && this.ankietaStudentForm.get('studentSpecialization').value != '') {
      if (this.ankietaStudentForm.get('studentInternshipStart').value < this.ankietaStudentForm.get('studentInternshipEnd').value) {
        this.questionNumber++;
        if (!this.isAdmin) {
          this.notifier.notify('info', 'Następne pytanie');
        }
      }
    }else if (this.questionNumber == 9 && this.ankietaStudentForm.get("answerTo91").value != '' && this.ankietaStudentForm.get("answerTo92").value != '' && this.ankietaStudentForm.get("answerTo93").value != '') {
      this.questionNumber++;
      if (!this.isAdmin) {
        this.notifier.notify('info', 'Następne pytanie');
      }
    }else if (this.questionNumber != 0 && this.questionNumber != 9 && this.ankietaStudentForm.get("answerTo" + this.questionNumber).value != '') {
      this.questionNumber++;
      if (!this.isAdmin) {
        this.notifier.notify('info', 'Następne pytanie');
      }
    }else{
      if (!this.isAdmin) {
        this.notifier.notify('warning', 'Wypełnij wymagane pola');
      }
    }
  }

  previousQuestion() {
    if (this.questionNumber > 0) {
      this.questionNumber--;
      if (!this.isAdmin) {
        this.notifier.notify('info', 'Poprzednie pytanie');
      }
    }
  }
}
