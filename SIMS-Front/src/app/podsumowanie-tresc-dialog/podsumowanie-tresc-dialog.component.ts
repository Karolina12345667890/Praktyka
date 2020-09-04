import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-podsumowanie-tresc-dialog',
  templateUrl: './podsumowanie-tresc-dialog.component.html',
  styleUrls: ['./podsumowanie-tresc-dialog.component.css']
})
export class PodsumowanieTrescDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<PodsumowanieTrescDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: string) {

  }

  ngOnInit() {
  }

}
