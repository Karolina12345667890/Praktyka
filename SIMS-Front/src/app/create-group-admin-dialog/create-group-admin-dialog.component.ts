import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-create-group-admin-dialog',
  templateUrl: './create-group-admin-dialog.component.html',
  styleUrls: ['./create-group-admin-dialog.component.css']
})
export class CreateGroupAdminDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<CreateGroupAdminDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: FormGroup) { }

  onNoClick() {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
