import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-edit-group-dialog',
  templateUrl: './edit-group-dialog.component.html',
  styleUrls: ['./edit-group-dialog.component.css']
})
export class EditGroupDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EditGroupDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: FormGroup, private fb: FormBuilder) {
  }

  onNoClick() {
    this.dialogRef.close();
  }
  ngOnInit() {
  }

}
