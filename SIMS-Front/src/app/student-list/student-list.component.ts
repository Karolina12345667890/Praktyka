import {Component, Inject, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { NotifierService } from 'angular-notifier';
import {ActivatedRoute, Router} from "@angular/router";
import {LoginServiceService} from "../login-service.service";
import {StudentDto} from "../models/StudentDto";
import {GroupDto} from "../models/GroupDto";
import {GroupApplicationDto} from "../models/GroupApplicationDto";
import {DatePipe} from "@angular/common";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {isNull, isUndefined} from "util";
import {EditCommentDialogComponent} from "../edit-comment-dialog/edit-comment-dialog.component";
import {isEmpty} from "rxjs/operators";
import {PagerService} from "../pager-service.service";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css'],
  providers: [DatePipe]
})
export class StudentListComponent implements OnInit {

  group: GroupDto = {
    id: 0,
    groupName: "",
    durationInWeeks: 0,
    startDate: "",
    isOpen: false,
    fieldOfStudy: "",
    formOfStudy: "",
    speciality: "",
    changed:false,
    students: [],
    groupAdminId : 0,
  groupAdminName : "",
  groupAdminSurname : "",

};
  studentList = new Array<StudentDto>();
  savedstudentList = new Array<StudentDto>();


  studentApplicationList = new Array<GroupApplicationDto>();
  private readonly notifier: NotifierService;
  isAdmin: boolean = false;
  pager: any = {};
  pagedItems: any[];
  rememberSort:string = "surname";

  constructor(private fb: FormBuilder, notifierService: NotifierService, private activatedroute: ActivatedRoute,
              private authService: LoginServiceService, private datePipe: DatePipe, public dialog: MatDialog,
              private router: Router, private pagerService: PagerService) {
    this.notifier = notifierService;


  }


  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
    this.load();
  }

  setPage(page: number) {
    // get pager object from service
    this.pager = this.pagerService.getPager(this.studentList.length, page);

    // get current page of items
    this.pagedItems = this.studentList.slice(this.pager.startIndex, this.pager.endIndex + 1);
  }

  load() {
    if (this.isAdmin) {
      let groupId: number;
      this.activatedroute.queryParams.subscribe(v =>
        groupId = v.groupId
      );

      this.authService.getResource('http://localhost:8080/api/group/' + groupId).subscribe(
        value => {
          //this.studentList.push(value.students);
          this.studentList = value.students;
          this.savedstudentList = value.students;
          this.setPage(this.pager.currentPage);
          //do student companyName
          this.studentList.forEach(v => {

            this.authService.getResource('http://localhost:8080' + v.documents[0].link).subscribe(
              value => {
                if (!isNull(value.companyName) && value.status == "ACCEPTED") {
                  v.companyName = value.companyName;
                }else{
                  v.companyName = "";
                }
              })
          });

          this.group = value;


          if(this.rememberSort == 'surname'){
            this.sortOrderSurname = !this.sortOrderSurname;
            this.sortSurname();
          }else if(this.rememberSort == 'companyname'){
            this.sortOrderCompanyName = ! this.sortOrderCompanyName;
            this.sortCompanyName();
          }

        },
        error => console.log(error),
      );

      this.authService.getResource('http://localhost:8080/api/group/' + groupId + '/applications').subscribe(
        value => {
          this.studentApplicationList = value;
          this.studentApplicationList.forEach(v => {
            v.date = this.datePipe.transform(v.date, 'yyyy-MM-dd').toString();
          })
        },
        error => console.log(error),
      );


    }
  }


  onAcceptClick(path: string) {
  console.log(path)
    this.authService.postResource('http://localhost:8080' + path, {}).subscribe(
      value => {
        this.load();
        this.notifier.notify("success", "Pomyślnie zaakceptowano studenta",)
      },
      error => {
        console.log(error)
        this.notifier.notify("error", "Coś poszło nietak",)
      },
    );


  }

  onDeclineClick(path: string) {

    this.authService.postResource('http://localhost:8080' + path, {}).subscribe(
      value => {
        this.load();
        this.notifier.notify("success", "Pomyślnie odrzucono studenta",)
      },
      error => {
        console.log(error)
        this.notifier.notify("error", "Coś poszło nietak",)
      },
    );


  }

  openDoc(id: number, docType: string) {
    this.router.navigate(['/' + docType], {queryParams: {id: id}});
  }


  showWarning(message: string, id: number, docType: string) {
    if (this.isAdmin) {
      const dialogRef = this.dialog.open(EditCommentDialogComponent, {
        width: '400px',
        data: message,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          console.log(result);
          this.authService.postResource('http://localhost:8080/api/document/' + docType + '/' + id + '/comment', result).subscribe(
            value => {
              console.log(value);
              this.notifier.notify("success", "Pomyślnie zmieniono uwage",);
              this.load();
            },
            error => {
              console.log(error)
              this.notifier.notify("error", error.error,)
            }
          );
        }
      });
    }

  }

  sortOrderSurname: boolean = true;

  sortSurname() {
    if (this.sortOrderSurname) {
      this.studentList.sort((a: StudentDto, b: StudentDto) => b.surname.localeCompare(a.surname))
    } else {
      this.studentList.sort((a: StudentDto, b: StudentDto) => a.surname.localeCompare(b.surname))
    }
    this.sortOrderSurname = !this.sortOrderSurname;
    this.pagedItems = this.studentList.slice(this.pager.startIndex, this.pager.endIndex + 1);
    this.rememberSort = "surname";
  }

  sortOrderCompanyName: boolean = true;
  sortCompanyName() {
    if (this.sortOrderCompanyName) {
      this.studentList.sort((a: StudentDto, b: StudentDto) => b.companyName.localeCompare(a.companyName))
    } else {
      this.studentList.sort((a: StudentDto, b: StudentDto) => a.companyName.localeCompare(b.companyName))
    }
    this.sortOrderCompanyName = !this.sortOrderCompanyName;
    this.pagedItems = this.studentList.slice(this.pager.startIndex, this.pager.endIndex + 1);
    this.rememberSort = "companyname"
  }

  foilterSurname:string = "";

  findSurname(){
    if(this.foilterSurname == ""){
      this.studentList = this.savedstudentList;
      this.setPage(1);
      return;
    }
    this.studentList = this.savedstudentList.filter( v =>
      v.surname.includes(this.foilterSurname)
    );
    this.setPage(1);
  }

  changeDocuments(studentId){

    this.authService.postResource('http://localhost:8080/api/group/'+this.group.id+'/users/job/'+studentId ,{}).subscribe(
      value => {
        this.load();
        this.notifier.notify("success","Pomyślnie Zmieniono dokumenty studenta",)
      },
      error =>{
        console.log(error);
        this.notifier.notify("error","Coś poszło nietak",)
      }
    );


  }

  dropStudent(studentId){

    this.authService.postResource('http://localhost:8080/api/group/'+this.group.id+'/users/drop/'+studentId ,{}).subscribe(
      value => {
        this.load();
        this.notifier.notify("success","Pomyślnie wyżucono studenta",)
      },
      error =>{
        console.log(error);
        this.notifier.notify("error","Coś poszło nietak",)
      }
    );


  }


}
