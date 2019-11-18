import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {GroupDto} from '../models/GroupDto';
import {isUndefined} from 'util';
import {Router} from '@angular/router';
import {LoginServiceService} from '../login-service.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css'],
  providers: [DatePipe]
})
export class GroupListComponent implements OnInit {


  private groupList = new Array<GroupDto>();

  constructor(private fb: FormBuilder, public dialog: MatDialog, private router: Router, private authService: LoginServiceService, private datePipe: DatePipe) {

  }

  ngOnInit() {

    this.authService.getResource('http://localhost:8080/api/groups').subscribe(
      value => {
        this.groupList = value;
        this.groupList.forEach( v => {
          v.startDate = this.datePipe.transform(v.startDate, 'yyyy-MM-dd').toString();
        })
      },
      error => console.log(error),
    );


  }


  openStudentList(id: number) {
    //  this.router.navigate(['/sl', {group : id}])
    this.router.navigate(['/sl'], {queryParams: {groupId: id}});
  }

  openEditDialog(item: GroupDto) {
    const dialogRef = this.dialog.open(AddAndEditGroupDialog, {
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
          value => console.log(value),
          error => console.log(error)
        );
        location.reload();
      }
    });
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(AddAndEditGroupDialog, {
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
          value => console.log(value),
          error => console.log(error)
        );
        location.reload();
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

  constructor(public dialogRef: MatDialogRef<AddAndEditGroupDialog>, @Inject(MAT_DIALOG_DATA) public data: FormGroup, private fb: FormBuilder) {


  }

  onNoClick() {
    this.dialogRef.close();
  }
}
