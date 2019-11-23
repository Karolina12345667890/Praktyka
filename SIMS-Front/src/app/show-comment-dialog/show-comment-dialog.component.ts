import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-show-comment-dialog',
  templateUrl: './show-comment-dialog.component.html',
  styleUrls: ['./show-comment-dialog.component.css']
})
export class ShowCommentDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ShowCommentDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: string) {

  }

  ngOnInit() {
  }

}
