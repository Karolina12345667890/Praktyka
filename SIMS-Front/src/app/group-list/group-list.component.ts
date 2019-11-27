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
import {PagerService} from "../pager-service.service";

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
  pager: any = {};
  pagedItems = new Array<GroupDto>();
  rememberSort :string="data";

  constructor(private fb: FormBuilder, public dialog: MatDialog, private router: Router, private authService: LoginServiceService, private datePipe: DatePipe, notifierService: NotifierService, private pagerService: PagerService) {
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
        this.setPage(this.pager.currentPage);
        this.groupList.forEach(v => {
          v.startDate = this.datePipe.transform(v.startDate, 'yyyy-MM-dd').toString();
        });
       if(this.rememberSort == "data") {
         this.sortOrderStartDate = !this.sortOrderStartDate;
         this.sortStartDate()
         // this.groupList.sort((a:GroupDto,b:GroupDto) => b.startDate.localeCompare(a.startDate));
       }
       else if(this.rememberSort == "status") {
         this.soerOrderStatus = !this.soerOrderStatus;
         this.sortStatus();
       }

      },
    error => console.log(error),
  );
  }

  setPage(page: number) {
    // get pager object from service
    this.pager = this.pagerService.getPager(this.groupList.length, page);

    // get current page of items
    this.pagedItems = this.groupList.slice(this.pager.startIndex, this.pager.endIndex + 1);
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
  soerOrderStatus: boolean = false;
  sortStatus(){
    if(this.soerOrderStatus)
    this.groupList.sort((a:GroupDto,b:GroupDto) =>  (a.isOpen === b.isOpen)? 0 : a.isOpen? -1 : 1);
    else
      this.groupList.sort((a:GroupDto,b:GroupDto) =>  (a.isOpen === b.isOpen)? 0 : a.isOpen? 1 : -1);
    this.soerOrderStatus = !this.soerOrderStatus;
    this.pagedItems = this.groupList.slice(this.pager.startIndex, this.pager.endIndex + 1);
    this.rememberSort = "status";
  }


  sortOrderStartDate : boolean = false;
  sortStartDate(){
    if(this.sortOrderStartDate)
     this.groupList.sort((a:GroupDto,b:GroupDto) => b.startDate.localeCompare(a.startDate));
    else
      this.groupList.sort((a:GroupDto,b:GroupDto) => a.startDate.localeCompare(b.startDate))
    this.sortOrderStartDate = !this.sortOrderStartDate;
    this.pagedItems = this.groupList.slice(this.pager.startIndex, this.pager.endIndex + 1);
    this.rememberSort = "data";
  }


  openEditDialog(item: GroupDto) {
    if(this.isAdmin) {
      const dialogRef = this.dialog.open(EditGroupDialogComponent, {
        width: '600px',
        data: this.fb.group({
          id: item.id,
          groupName: new FormControl(item.groupName, [Validators.required,]),
          startDate: new FormControl(item.startDate, [Validators.required,]),
          durationInWeeks: new FormControl(item.durationInWeeks, [Validators.required,]),
          fieldOfStudy : new FormControl(item.fieldOfStudy, [Validators.required,]),
          formOfStudy :new FormControl(item.formOfStudy, [Validators.required,]),
          speciality : new FormControl(item.speciality, [Validators.required,]),
          isOpen: item.isOpen,
        })
      });


      dialogRef.afterClosed().subscribe(result => {

        if (!isUndefined(result)) {
          let body = result.value;
          this.authService.postResource('http://localhost:8080/api/groups', body).subscribe(
            value => {
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
        width: '600px',
        data: this.fb.group({
          id: null,
          groupName: new FormControl('', [Validators.required,]),
          startDate: new FormControl('', [Validators.required,]),
          durationInWeeks: new FormControl('', [Validators.required,]),
          fieldOfStudy : new FormControl('', [Validators.required,]),
          formOfStudy :new FormControl('FULL_TIME', [Validators.required,]),
          speciality : new FormControl('', [Validators.required,]),
          isOpen: false,
        })
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          let body = result.value;
          this.authService.postResource('http://localhost:8080/api/groups', body).subscribe(
            value => {
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


