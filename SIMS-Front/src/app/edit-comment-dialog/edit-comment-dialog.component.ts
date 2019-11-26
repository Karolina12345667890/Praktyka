import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-edit-comment-dialog',
  templateUrl: './edit-comment-dialog.component.html',
  styleUrls: ['./edit-comment-dialog.component.css']
})
export class EditCommentDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EditCommentDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: string) {

  }

  ngOnInit() {
  }

}
