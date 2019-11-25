import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {GroupDto} from '../models/GroupDto';
import {isUndefined} from 'util';
import {Router} from '@angular/router';
import {LoginServiceService} from '../login-service.service';

import { DatePipe } from '@angular/common';
import {NotifierService} from "angular-notifier";
import {EditGroupDialogComponent} from "../edit-group-dialog/edit-group-dialog.component";

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css'],
  providers: [DatePipe]
})
export class GroupListComponent implements OnInit {

  private readonly notifier: NotifierService;
  private groupList = new Array<GroupDto>();
  isAdmin:boolean = false;

  constructor(private fb: FormBuilder, public dialog: MatDialog, private router: Router, private authService: LoginServiceService, private datePipe: DatePipe, notifierService: NotifierService) {
    this.notifier = notifierService;
  }

  ngOnInit() {


    this.isAdmin = this.authService.isAdmin();
  this.load();
  }

  load(){

    this.authService.getResource('http://localhost:8080/api/groups').subscribe(
      value => {
        this.groupList = value;
        this.groupList.forEach(v => {
          v.startDate = this.datePipe.transform(v.startDate, 'yyyy-MM-dd').toString();
        });
      },
    error => console.log(error),
  );
  }


  openStudentList(id: number) {
    //  this.router.navigate(['/sl', {group : id}])
    if(this.isAdmin)
    this.router.navigate(['/sl'], {queryParams: {groupId: id}});
  }


  joinGroup(id:number){
    this.authService.postResource('http://localhost:8080/api/group/'+id+'/applications/new', {}).subscribe(
      value => {console.log(value)
        this.notifier.notify("success","Pomyślnie dołączono do grupy",)
      },
      error =>{ console.log(error)
        this.notifier.notify("error",error.error,)
      }
    );
  }


  openEditDialog(item: GroupDto) {
    if(this.isAdmin) {
      const dialogRef = this.dialog.open(EditGroupDialogComponent, {
        width: '400px',
        data: this.fb.group({
          id: item.id,
          groupName: new FormControl(item.groupName, [Validators.required,]),
          startDate: new FormControl(item.startDate, [Validators.required,]),
          durationInWeeks: new FormControl(item.durationInWeeks, [Validators.required,]),
          isOpen: item.isOpen,
        })
      });


      dialogRef.afterClosed().subscribe(result => {

        if (!isUndefined(result)) {
          let body = result.value;
          this.authService.postResource('http://localhost:8080/api/groups', body).subscribe(
            value => {
              console.log(value);
              this.load();
              this.notifier.notify("success","Pomyślnie edytowano grupe",)
            },
            error =>{ console.log(error);
              this.notifier.notify("error","Coś poszło nietak",)
            }
          );

        }
      });
    }
  }

  openAddDialog() {
    if (this.isAdmin) {
      const dialogRef = this.dialog.open(EditGroupDialogComponent, {
        width: '400px',
        data: this.fb.group({
          id: null,
          groupName: new FormControl('', [Validators.required,]),
          startDate: new FormControl('', [Validators.required,]),
          durationInWeeks: new FormControl('', [Validators.required,]),
          isOpen: false,
        })
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          let body = result.value;
          this.authService.postResource('http://localhost:8080/api/groups', body).subscribe(
            value => {
              console.log(value);
              this.load();
              this.notifier.notify("success","Pomyślnie dodano nową grupe",)
            },
            error =>{
              console.log(error);
              this.notifier.notify("error","Coś poszło nietak",)
            }
          );

        }
      });
    }
  }
}


