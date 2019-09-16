import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-dziennik-praktyk',
  templateUrl: './dziennik-praktyk.component.html',
  styleUrls: ['./dziennik-praktyk.component.css']
})
export class DziennikPraktykComponent implements OnInit {

  periodFrom: Date;
  periodTo: Date;
  days: Date;
  dayOfTheWeek;
  diary: FormArray;
  dateFrom:boolean=false;
  dateTo:boolean=false;

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

  constructor(private fb: FormBuilder) {
    this.diaryGroup = this.fb.group({
      studentName: new FormControl('', [ Validators.required, ]),
      studentSurname: new FormControl('', [ Validators.required, ]),
      studentAlbumNumber: new FormControl('', [ Validators.required, ]),
      companyName: new FormControl('', [ Validators.required, ]),
      periodFrom: new FormControl('', [ Validators.required, ]),
      periodTo: new FormControl('', [ Validators.required, ]),
      diary: this.fb.array([])
    });
  }


  get studentName() { return this.diaryGroup.get('studentName')}
  get studentSurname() { return this.diaryGroup.get('studentSurname')}
  get studentAlbumNumber() { return this.diaryGroup.get('studentAlbumNumber')}
  get companyName() { return this.diaryGroup.get('companyName')}
  get getperiodFrom() { return this.diaryGroup.get('periodFrom')}
  get getperiodTo() { return this.diaryGroup.get('periodTo')}



  ngOnInit() {
  }

  createItem(date: string, dayName: string) {
    return this.fb.group({
      date: new FormControl({value: date, disabled: true}, Validators.required),
      text:'',
      dayName: new FormControl({value: dayName, disabled: true}, Validators.required),
    });
  }

  addItem(date: string, dayName: string) {
    this.diary = this.diaryGroup.get('diary') as FormArray;
    this.diary.push(this.createItem(date, dayName));

  }

  onChange(value: string) {

    if (this.periodTo==null|| new Date(value).getTime()<this.periodTo.getTime()){


      if (this.periodFrom != null && this.periodTo != null) {
        if (this.periodFrom < new Date(value)) {
          let newDays = (new Date(value).getTime() - this.periodFrom.getTime()) / (1000 * 3600 * 24);
          for (let i: number = 0; i < newDays; i++) {
            this.diary.removeAt(0);
          }
        } else if (this.periodFrom > new Date(value)) {
          let newDays = (this.periodFrom.getTime() - new Date(value).getTime()) / (1000 * 3600 * 24);
          let days:Date=this.days;
          for (let i: number = 0; i < newDays; i++) {
            days = new Date(days.getTime() - (1000 * 3600 * 24));
            this.periodFrom = new Date(this.periodFrom.getTime() - (1000 * 3600 * 24));
            days = this.periodFrom;
            this.diary.insert(0, this.createItem(days.toLocaleDateString(), this.daysName[this.periodFrom.getDay()].text));
          }
        }
      }
      this.periodFrom = new Date(value);

    }
if(this.dateFrom==false) {
  this.dayOfTheWeek = this.periodFrom.getDay();
  this.days = this.periodFrom;
  this.createTable();
}
    this.dateFrom=true;

  }

  onChange1(value: string) {

    if (this.periodFrom==null ||  this.periodFrom.getTime() <new Date(value).getTime()) {

      if (this.periodTo < new Date(value)) {
        let newDays = (new Date(value).getTime() - this.periodTo.getTime()) / (1000 * 3600 * 24);
        for (let i: number = 0; i < newDays; i++) {
          this.addItem(this.days.toLocaleDateString(), this.daysName[this.dayOfTheWeek].text);
          this.days = new Date(this.days.getTime() + (1000 * 3600 * 24));
          this.dayOfTheWeek=this.days.getDay();

        }
        this.periodTo = new Date(value);
      } else if (this.periodTo > new Date(value)) {
        let newDays = (this.periodTo.getTime() - new Date(value).getTime()) / (1000 * 3600 * 24);
        this.periodTo = new Date(value);
        for (let i: number = 0; i < newDays; i++) {
          this.days = new Date(this.days.getTime() - (1000 * 3600 * 24));
          this.dayOfTheWeek=this.days.getDay();
          this.diary.removeAt(this.diary.length - 1);
        }
      }


    this.periodTo = new Date(value);
    }
        if(this.dateFrom==true && this.dateTo==false) {
          this.createTable();
      }
    this.dateTo=true;

    }


  private createTable() {
    if (this.periodFrom != null && this.periodTo != null) {
      let daysNumber = (this.periodTo.getTime() - this.periodFrom.getTime()) / (1000 * 3600 * 24) + 1;
      for (let i: number = daysNumber; i > 0; i--) {
        this.addItem(this.days.toLocaleDateString(), this.daysName[this.dayOfTheWeek].text);
        this.days = new Date(this.days.getTime() + (1000 * 3600 * 24));
        this.dayOfTheWeek=this.days.getDay();
      }
    }
  }



  onSubmit() {
    console.log(this.diaryGroup);
  }

}
