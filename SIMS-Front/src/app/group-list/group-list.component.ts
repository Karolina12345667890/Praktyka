import {Component, Inject, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {GroupDto} from "../models/GroupDto";
import { isNull, isUndefined } from 'util';
import {Router} from "@angular/router";
import {LoginServiceService} from "../login-service.service";
import {DocumentDto} from "../models/DocumentDto";

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {

  //groupListForm: FormGroup;
 // groupList: FormArray;
  private groupList = new Array<GroupDto>();

  constructor(private fb: FormBuilder, public dialog: MatDialog,private router: Router,private authService: LoginServiceService) {

  }

  ngOnInit() {
    //this.groupList= this.fb.array([]);
    //zadmiast fill pobranie z servera
    this.authService.getResource('http://localhost:8080/api/groups').subscribe(
      value => {
        this.groupList = value;

      },
      error => console.log(error),
    );


  }



  openStudentList(id:number){
  //  this.router.navigate(['/sl', {group : id}])
    this.router.navigate(['/sl'], {queryParams: { groupId : id }});
  }

  openEditDialog(item: GroupDto) {
    const dialogRef = this.dialog.open(AddAndEditGroupDialog, {
      width: '400px',
      data: this.fb.group({  id : item.id,
        groupName: new FormControl(item.groupName, [Validators.required,]),
        startDate: new FormControl(item.startDate, [Validators.required,]),
        durationInWeeks: new FormControl(item.durationInWeeks, [Validators.required,]),
        isOpen: item.isOpen,})
    });


    dialogRef.afterClosed().subscribe(result => {

      if(!isUndefined(result)){
        this.authService.postResource("http://localhost:8080/api/groups",result.value);
        console.log(result.value);
      }
    });
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(AddAndEditGroupDialog, {
      width: '400px',
      data: this.fb.group({  id : null,
        groupName: new FormControl('', [Validators.required,]),
        startDate: new FormControl('', [Validators.required,]),
        durationInWeeks: new FormControl('', [Validators.required,]),
        isOpen: false,})
    });


    dialogRef.afterClosed().subscribe(result => {
      if(!isUndefined(result)){
        console.log(result.value);
        this.authService.postResource("http://localhost:8080/api/groups",result.value);

      }
    });
  }
  }



@Component({
  selector: 'group-list.addAndEditGroupDialog.component',
  templateUrl: 'group-list.addAndEditGroupDialog.component.html',
  styleUrls: ['./group-list.component.css']
})
export class AddAndEditGroupDialog {

  constructor( public dialogRef: MatDialogRef<AddAndEditGroupDialog>,@Inject(MAT_DIALOG_DATA) public data: FormGroup,private fb: FormBuilder) {



  }

  onNoClick() {
    this.dialogRef.close();
  }
}
