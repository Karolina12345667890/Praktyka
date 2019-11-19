import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { NotifierService } from 'angular-notifier';
import {ActivatedRoute} from "@angular/router";
import {LoginServiceService} from "../login-service.service";
import {StudentDto} from "../models/StudentDto";
import {GroupDto} from "../models/GroupDto";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {

  group: GroupDto;
  studentList = new Array<StudentDto>()
  private readonly notifier: NotifierService;
  isAdmin:boolean = false;

  constructor(private fb: FormBuilder, notifierService: NotifierService,private router: ActivatedRoute,private authService: LoginServiceService ) {

    this.notifier = notifierService;


    //this.fill();
    //console.log(this.studentList)
  }


  //do testów
  // fill(){
  //   this.studentList.push(
  //     this.studentListForm = this.fb.group({
  //       id: '1',
  //       studentName: 'Ala',
  //       studentSurname: 'Woj',
  //       studentSpecialization: 'Informatyka',
  //       periodFrom: '11.11.2001',
  //       periodTo: '11.12.2001',
  //       companyName:  'Sony Corporation',
  //       documentOswiadczenie: this.fb.group({
  //         status: 'New',
  //         warning: '',
  //       }),
  //       documentPorozumienie: this.fb.group({
  //         status: '',
  //         warning: '123',
  //       }),
  //       documentZaswiadczenie: this.fb.group({
  //         status: 'Accepted',
  //         warning: '',
  //       }),
  //       documentDziennik: this.fb.group({
  //         status: '',
  //         warning: '',
  //       }),
  //     })
  //   );
  //
  //   this.studentList.push(
  //     this.studentListForm = this.fb.group({
  //       id: '2',
  //       studentName: 'Tomek',
  //       studentSurname: 'Tar',
  //       studentSpecialization: 'Chemia',
  //       periodFrom: '11.11.2001',
  //       periodTo: '11.12.2001',
  //       companyName:  'mBank, Bankowość Detaliczna BRE Banku SA',
  //       documentOswiadczenie: this.fb.group({
  //         status: 'New',
  //         warning: '',
  //       }),
  //       documentPorozumienie: this.fb.group({
  //         status: '',
  //         warning: '123',
  //       }),
  //       documentZaswiadczenie: this.fb.group({
  //         status: 'Accepted',
  //         warning: '',
  //       }),
  //       documentDziennik: this.fb.group({
  //         status: '',
  //         warning: '',
  //       }),
  //     })
  //   );
  //
  //   this.studentList.push(
  //     this.studentListForm = this.fb.group({
  //       id: '3',
  //       studentName: 'Andrzej',
  //       studentSurname: 'Nowosielski',
  //       studentSpecialization: 'Matematyka',
  //       periodFrom: '11.11.2001',
  //       periodTo: '11.12.2001',
  //       companyName:  'Uniwersytet Przyrodniczo Humanistyczny w Siedlcach wydział Nauk Ścisłych',
  //       documentOswiadczenie: this.fb.group({
  //         status: 'New',
  //         warning: '',
  //       }),
  //       documentPorozumienie: this.fb.group({
  //         status: 'Declined',
  //         warning: '123',
  //       }),
  //       documentZaswiadczenie: this.fb.group({
  //         status: 'Accepted',
  //         warning: '',
  //       }),
  //       documentDziennik: this.fb.group({
  //         status: '',
  //         warning: '',
  //       }),
  //     })
  //   )
  // }

  onClick(path:string,document:string){
    console.log(path+" "+ document);
    this.authService.getResource('http://localhost:8080'+path).subscribe(
      value => {
        console.log(value);
      },
      error => console.log(error),
    );

  }

  showWarning(warning:string){
    alert( warning );

  }

  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();

    if(this.isAdmin) {
      let groupId: number;
      this.router.queryParams.subscribe(v =>
        groupId = v.groupId
      );

      this.authService.getResource('http://localhost:8080/api/group/' + groupId).subscribe(
        value => {
          this.studentList = value.students;
          this.group = value;


        },
        error => console.log(error),
      );
    }

  }


}
