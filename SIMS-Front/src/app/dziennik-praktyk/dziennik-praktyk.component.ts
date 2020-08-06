import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {NotifierService} from 'angular-notifier';
import {HttpClient} from "@angular/common/http";
import {LoginServiceService} from "../login-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {oswiadczenieDto} from "../models/oswiadczenieDto";
import {dziennikPraktykDto} from "../models/DziennikPraktykDto";
import {diaryDto} from "../models/DiaryDto";
import {DatePipe} from "@angular/common";
import {EditCommentDialogComponent} from "../edit-comment-dialog/edit-comment-dialog.component";
import {isUndefined} from "util";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-dziennik-praktyk',
  templateUrl: './dziennik-praktyk.component.html',
  styleUrls: ['./dziennik-praktyk.component.css']
})
export class DziennikPraktykComponent implements OnInit {

  id: number;
  isAdmin: boolean = false;
  periodFrom: Date;
  periodTo: Date;
  days: Date;
  comment: string = "";
  diary: FormArray;
  dateFrom: boolean = false;
  dateTo: boolean = false;
  private readonly notifier: NotifierService;

  daysName: Array<{ id: number, text: string }> = [
    {id: 0, text: 'Niedziela'},
    {id: 1, text: 'Poniedziałek'},
    {id: 2, text: 'Wotrek'},
    {id: 3, text: 'Środa'},
    {id: 4, text: 'Czwartek'},
    {id: 5, text: 'Piątek'},
    {id: 6, text: 'Sobota'},
  ];
  diaryGroup: FormGroup;

  constructor(private fb: FormBuilder, notifierService: NotifierService, private authService: LoginServiceService, private router: Router, private httpClient: HttpClient, private activatedroute: ActivatedRoute, private datePipe: DatePipe, public dialog: MatDialog) {
    this.activatedroute.queryParams.subscribe(v =>
      this.id = v.id
    );


    this.diaryGroup = this.fb.group({
      studentName: '',
      studentSurname: '',
      studentAlbumNumber: '',
      companyName: '',
      periodFrom: '',
      periodTo: '',
      diary: this.fb.array([])
    });


    this.authService.getResource('http://localhost:8080/api/document/dziennikpraktyk/' + this.id).subscribe(
      value => {

        this.diaryGroup = this.fb.group({
          studentName: '',
          studentSurname: '',
          studentAlbumNumber: new FormControl(value.studentAlbumNumber, [Validators.required]),
          companyName: new FormControl(value.companyName, [Validators.required]),
          periodFrom: new FormControl(this.datePipe.transform(value.periodFrom, 'yyyy-MM-dd').toString(), [Validators.required]),
          periodTo: new FormControl(this.datePipe.transform(value.periodTo, 'yyyy-MM-dd').toString(), [Validators.required]),
          diary: this.fb.array([])
        });

        value.diary.forEach(d => {
          this.addExistingItem(d.date, d.text);
        });

        this.periodFrom = new Date(value.periodFrom );
        this.periodTo = new Date(value.periodTo);
        this.days = this.periodFrom;
        this.dateFrom = true;
        this.dateTo = true;

        this.authService.getResource('http://localhost:8080/api/user/' + value.ownerId).subscribe(
          val => {
            this.diaryGroup.get("studentName").setValue(val.name);
            this.diaryGroup.get("studentSurname").setValue(val.surname);
          });

        if (value.diary[0] == null) { this.createTable(true); }
        else { this.createTable(false); }
      },
      error => console.log(error),
    );

    this.notifier = notifierService;
  }


  get getstudentName() {
    return this.diaryGroup.get('studentName')
  }

  get getstudentSurname() {
    return this.diaryGroup.get('studentSurname')
  }

  get getstudentAlbumNumber() {
    return this.diaryGroup.get('studentAlbumNumber')
  }

  get getcompanyName() {
    return this.diaryGroup.get('companyName')
  }

  get getperiodFrom() {
    return this.diaryGroup.get('periodFrom')
  }

  get getperiodTo() {
    return this.diaryGroup.get('periodTo')
  }


  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
  }

  createItem(date: string, dayName: string) {
    const dateElements = date.split('.');
    const correctDate = dateElements[1] + "-" + dateElements[0] + "-" + dateElements[2];

    return this.fb.group({
      date: this.datePipe.transform(new Date(Date.parse(correctDate)), 'yyyy-MM-dd').toString(),
      text: '',
      dayName: new FormControl({value: dayName, disabled: true}, Validators.required),
    });
  }

  loadItem(date: string, text: string) {
    const dateElements = date.split('.');
    const correctDate = dateElements[1] + "-" + dateElements[0] + "-" + dateElements[2];
    const usedDate = new Date(Date.parse(correctDate));

    return this.fb.group({
      date: this.datePipe.transform(usedDate, 'yyyy-MM-dd').toString(),
      text: text,
      dayName: new FormControl({value: this.daysName[usedDate.getDay()].text, disabled: true}, Validators.required),
    });
  }

  addExistingItem(date: Date, text: string) {
    const existingDate = new Date(date).getUTCDate() + '.' + Number(new Date(date).getUTCMonth()+1) + '.' + new Date(date).getUTCFullYear();

    this.diary = this.diaryGroup.get('diary') as FormArray;
    this.diary.push(this.loadItem(existingDate, text));
  }

  addItem(date: string, dayName: string) {
    this.diary = this.diaryGroup.get('diary') as FormArray;
    this.diary.push(this.createItem(date, dayName));
  }

  onChange(value: string) {
    if (this.periodTo == null || new Date(value).getTime() < this.periodTo.getTime()) {

      if (this.periodFrom != null && this.periodTo != null) {
        if (this.periodFrom < new Date(value)) {
          let newDays = (new Date(value).getTime() - this.periodFrom.getTime()) / (1000 * 3600 * 24);

          for (let i: number = 0; i < newDays; i++) {
            this.diary.removeAt(0);
          }

          this.notifier.notify('info', 'Tabela zmieniona');
        }
        else if (this.periodFrom > new Date(value)) {
          let newDays = (this.periodFrom.getTime() - new Date(value).getTime()) / (1000 * 3600 * 24);

          for (let i: number = 0; i < newDays; i++) {
            this.periodFrom = new Date(this.periodFrom.getTime() - (1000 * 3600 * 24));
            this.diary.insert(0, this.createItem(this.periodFrom.toLocaleDateString(), this.daysName[this.periodFrom.getDay()].text));
          }

          this.notifier.notify('info', 'Tabela zmieniona');
        }
      }

      this.periodFrom = new Date(value);
    }

    if (this.dateFrom == false) {
      this.days = this.periodFrom;
      this.createTable(true);
    }

    this.dateFrom = true;
  }

  onChange1(value: string) {
    if (this.periodFrom == null || this.periodFrom.getTime() < new Date(value).getTime()) {

      if (this.periodTo < new Date(value)) {
        let newDays = (new Date(value).getTime() - this.periodTo.getTime()) / (1000 * 3600 * 24);

        for (let i: number = 0; i < newDays; i++) {
          this.addItem(this.days.toLocaleDateString(), this.daysName[this.days.getDay()].text);
          this.days = new Date(this.days.getTime() + (1000 * 3600 * 24));
        }

        this.periodTo = new Date(value);
        this.notifier.notify('info', 'Tabela zmieniona');
      }
      else if (this.periodTo > new Date(value)) {
        let newDays = (this.periodTo.getTime() - new Date(value).getTime()) / (1000 * 3600 * 24);
        this.periodTo = new Date(value);

        for (let i: number = 0; i < newDays; i++) {
          this.days = new Date(this.days.getTime() - (1000 * 3600 * 24));
          this.diary.removeAt(this.diary.length - 1);
        }

        this.notifier.notify('info', 'Tabela zmieniona');
      }

      this.periodTo = new Date(value);
    }

    if (this.dateFrom == true && this.dateTo == false) {
      this.createTable(true);
    }

    this.dateTo = true;
  }


  private createTable(additems : boolean) {
    if (this.periodFrom != null && this.periodTo != null) {
      this.notifier.notify('info', 'Tabela stworzona');
      let daysNumber = (this.periodTo.getTime() - this.periodFrom.getTime()) / (1000 * 3600 * 24) + 1;
      for (let i: number = daysNumber; i > 0; i--) {
        if(additems)
        this.addItem(this.days.toLocaleDateString(), this.daysName[this.days.getDay()].text);
        this.days = new Date(this.days.getTime() + (1000 * 3600 * 24));
      }
    }
  }


  onSubmit() {
    let body: dziennikPraktykDto = {
      periodFrom: this.datePipe.transform(this.diaryGroup.value.periodFrom, 'yyyy-MM-dd').toString(),
      periodTo: this.datePipe.transform(this.diaryGroup.value.periodTo, 'yyyy-MM-dd').toString(),
      companyName: this.diaryGroup.value.companyName,
      studentAlbumNumber: this.diaryGroup.value.studentAlbumNumber,
      diary: this.diaryGroup.value.diary,
    };

    this.authService.postResource('http://localhost:8080/api/document/dziennikpraktyk/' + this.id, body).subscribe(
      value => {
        console.log(value)
        this.notifier.notify("success", "Pomyślnie wysłano dokument Dziennik Praktyk")
        this.router.navigate(["/home"]);
      },
      error => {
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );

  }

  accept() {
    this.authService.postResource('http://localhost:8080/api/document/dziennikpraktyk/' + this.id + '/accept', {}).subscribe(
      value => {
        console.log(value);
        this.notifier.notify("success", "Pomyślnie zaakceptowano dokument Dziennik Praktyk");
      },
      error => {
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
  }

  decline() {
    this.authService.postResource('http://localhost:8080/api/document/dziennikpraktyk/' + this.id + '/decline', {}).subscribe(
      value => {
        console.log(value)
        this.notifier.notify("success", "Pomyślnie odrzucono dokument Dziennik Praktyk");
      },
      error => {
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
  }


  warning() {
    if (this.isAdmin) {
      const dialogRef = this.dialog.open(EditCommentDialogComponent, {
        width: '400px',
        data: this.comment,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          this.authService.postResource('http://localhost:8080/api/document/dziennikpraktyk/' + this.id + '/comment', result).subscribe(
            value => {
              console.log(value);
              this.notifier.notify("success", "Pomyślnie dodano uwagę");
              this.decline();
            },
            error => {
              console.log(error)
              this.notifier.notify("error", error.error)
            }
          );
        }
      });
    }
  }

}
