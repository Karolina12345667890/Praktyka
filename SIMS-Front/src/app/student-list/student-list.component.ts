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
import {isUndefined} from "util";
import {EditCommentDialogComponent} from "../edit-comment-dialog/edit-comment-dialog.component";
import {isEmpty} from "rxjs/operators";

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
  fieldOfStudy : "",
  formOfStudy :"",
  speciality : "",
  students : []
};
  studentList = new Array<StudentDto>();
  studentForm: FormGroup;

  studentApplicationList = new Array<GroupApplicationDto>();
  private readonly notifier: NotifierService;
  isAdmin:boolean = false;

  constructor(private fb: FormBuilder, notifierService: NotifierService,private activatedroute: ActivatedRoute,private authService: LoginServiceService, private datePipe: DatePipe , public dialog: MatDialog ,private router: Router) {
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
     this.activatedroute.queryParams.subscribe(v =>
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



  openDoc(id:number,docType:string) {
    this.router.navigate(['/'+docType], {queryParams: {id: id}});
  }



  showWarning(message: string,id :number) {
    if(this.isAdmin) {
      const dialogRef = this.dialog.open(EditCommentDialogComponent, {
        width: '400px',
        data: message,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          this.authService.postResource('http://localhost:8080/api/document/oswiadczenie/'+id+'/comment', result).subscribe(
            value => { console.log(value);
              this.notifier.notify("success","Pomyślnie zmieniono uwage",);
              this.load();
            },
            error =>{ console.log(error)
              this.notifier.notify("error",error.error,)
            }
          );
        }
    });
  }

}
}

