import {Component, Inject, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { NotifierService } from 'angular-notifier';
import {ActivatedRoute} from "@angular/router";
import {LoginServiceService} from "../login-service.service";
import {StudentDto} from "../models/StudentDto";
import {GroupDto} from "../models/GroupDto";
import {GroupApplicationDto} from "../models/GroupApplicationDto";
import {DatePipe} from "@angular/common";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {isUndefined} from "util";
import {EditCommentDialogComponent} from "../edit-comment-dialog/edit-comment-dialog.component";

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
  students : []
};
  studentList = new Array<StudentDto>();
  studentForm: FormGroup;

  studentApplicationList = new Array<GroupApplicationDto>();
  private readonly notifier: NotifierService;
  isAdmin:boolean = false;

  constructor(private fb: FormBuilder, notifierService: NotifierService,private router: ActivatedRoute,private authService: LoginServiceService, private datePipe: DatePipe , public dialog: MatDialog) {
    this.notifier = notifierService;

    this.studentForm = this.fb.group({
      studentArray: this.fb.array([])
    });
  }


  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
    this.studentList = [];
    this.load();
  }

 load() {
   if (this.isAdmin) {
     let groupId: number;
     this.router.queryParams.subscribe(v =>
       groupId = v.groupId
     );

     this.authService.getResource('http://localhost:8080/api/group/' + groupId).subscribe(
       value => {
         this.studentList.push(value.students);
         this.studentList = value.students;
         this.group = value;
         console.log(this.studentList);

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


  onAcceptClick(path:string){

    this.authService.postResource('http://localhost:8080'+path, {}).subscribe(
      value => {
           this.load();
            this.notifier.notify("success","Pomyślnie zaakceptowano studenta",)
          },
          error => { console.log(error)
            this.notifier.notify("error","Coś poszło nietak",)
          },
    );



  }



  onClick(path:string,document:string){
 //   console.log(path+" "+ document);
    this.authService.getResource('http://localhost:8080'+path).subscribe(
      value => {
        console.log(value);
      },
      error => console.log(error),
    );

  }



  showWarning(message: string) {
    if(this.isAdmin) {
      const dialogRef = this.dialog.open(EditCommentDialogComponent, {
        width: '400px',
        data: message,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          //zmiana komentarza do dokumentu
          this.notifier.notify("success","Pomyślnie zmieniono uwage",)
         console.log(result);
        }
    });
  }

}
}

