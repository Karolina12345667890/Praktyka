import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-dziennik-praktyk',
  templateUrl: './dziennik-praktyk.component.html',
  styleUrls: ['./dziennik-praktyk.component.css']
})
export class DziennikPraktykComponent implements OnInit {

  periodFrom: Date;
  periodTo: Date;
  days: Date;

  diary: FormArray;
  dateFrom: boolean = false;
  dateTo: boolean = false;
  private readonly notifier: NotifierService;

  daysName: Array<{ id: number, text: string }> = [
    {id: 0, text: "Niedziela"},
    {id: 1, text: "Poniedziałek"},
    {id: 2, text: "Wotrek"},
    {id: 3, text: "Środa"},
    {id: 4, text: "Czwartek"},
    {id: 5, text: "Piątek"},
    {id: 6, text: "Sobota"},
  ];
  diaryGroup: FormGroup;

  constructor(private fb: FormBuilder, notifierService: NotifierService) {
    this.notifier = notifierService;
    this.diaryGroup = this.fb.group({
      studentName: new FormControl('', [Validators.required,]),
      studentSurname: new FormControl('', [Validators.required,]),
      studentAlbumNumber: new FormControl('', [Validators.required,]),
      companyName: new FormControl('', [Validators.required,]),
      periodFrom: new FormControl('', [Validators.required,]),
      periodTo: new FormControl('', [Validators.required,]),
      diary: this.fb.array([])
    });
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
  }

  createItem(date: string, dayName: string) {
    return this.fb.group({
      date: new FormControl({value: date, disabled: true}, Validators.required),
      text: '',
      dayName: new FormControl({value: dayName, disabled: true}, Validators.required),
    });
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
          this.notifier.notify( 'info', 'Tabela zmieniona' );
        } else if (this.periodFrom > new Date(value)) {
          let newDays = (this.periodFrom.getTime() - new Date(value).getTime()) / (1000 * 3600 * 24);
          for (let i: number = 0; i < newDays; i++) {
            this.periodFrom = new Date(this.periodFrom.getTime() - (1000 * 3600 * 24));
            this.diary.insert(0, this.createItem(this.periodFrom.toLocaleDateString(), this.daysName[this.periodFrom.getDay()].text));
          }
          this.notifier.notify( 'info', 'Tabela zmieniona' );
        }
      }
      this.periodFrom = new Date(value);

    }
    if (this.dateFrom == false) {
      this.days = this.periodFrom;
      this.createTable();
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
        this.notifier.notify( 'info', 'Tabela zmieniona' );
      } else if (this.periodTo > new Date(value)) {
        let newDays = (this.periodTo.getTime() - new Date(value).getTime()) / (1000 * 3600 * 24);
        this.periodTo = new Date(value);
        for (let i: number = 0; i < newDays; i++) {
          this.days = new Date(this.days.getTime() - (1000 * 3600 * 24));
          this.diary.removeAt(this.diary.length - 1);
        }
        this.notifier.notify( 'info', 'Tabela zmieniona' );
      }


      this.periodTo = new Date(value);
    }
    if (this.dateFrom == true && this.dateTo == false) {
      this.createTable();
    }
    this.dateTo = true;

  }


  private createTable() {
    if (this.periodFrom != null && this.periodTo != null) {
      this.notifier.notify( 'info', 'Tabela stworzona' );
      let daysNumber = (this.periodTo.getTime() - this.periodFrom.getTime()) / (1000 * 3600 * 24) + 1;
      for (let i: number = daysNumber; i > 0; i--) {
        this.addItem(this.days.toLocaleDateString(), this.daysName[this.days.getDay()].text);
        this.days = new Date(this.days.getTime() + (1000 * 3600 * 24));
      }
    }
  }


  onSubmit() {
    console.log(this.diaryGroup);
    this.notifier.notify( 'success', 'Pomyślnie wysłano dziennik' );
  }

}
