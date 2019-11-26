import {Component, OnInit} from '@angular/core';
import {LoginServiceService} from '../login-service.service';
import {DocumentDto} from '../models/DocumentDto';
import {isUndefined} from "util";
import {MatDialog} from "@angular/material/dialog";
import {ShowCommentDialogComponent} from "../show-comment-dialog/show-comment-dialog.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-component',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  private myDocumentList = new Array<DocumentDto>();


  constructor(private authService: LoginServiceService, public dialog: MatDialog,private router: Router) {
  }

  ngOnInit() {
    if(this.authService.isLoggedIn()) {
      this.authService.getResource('http://localhost:8080/api/document/list').subscribe(
        value => {
          this.myDocumentList = value;
            console.log(value);
        },
        error => console.log(error),
      );
    }

  }




  openDoc(id:number,docType:string) {
    this.router.navigate(['/'+docType], {queryParams: {id: id}});
  }

  onClick(path:string,documentType:string){
    this.authService.getResource2('http://localhost:8080'+path+'/pdf').subscribe(
      value => {
        this.myDocumentList = value;
        console.log(value);
        const objectURL = window.URL.createObjectURL(value);
        // window.open(objectURL, '_blank');
        console.log(objectURL);
        var link = document.createElement('a');
        link.href = objectURL;
        link.download = documentType+'.pdf'; //domyslna nazwa pliku
        link.click();
        setTimeout(function() {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(objectURL);
        }, 100);
      },
      error => console.log(error),
    );
  }
  // onClick(path:string,document:string){
  //   console.log(path+" "+ document);
  //
  // }

  showWarning(message: string) {

      const dialogRef = this.dialog.open(ShowCommentDialogComponent, {
        width: '400px',
        data: message,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          //zmiana komentarza do dokumentu
          alert(result);
        }
      });


  }




}



export class Response {
  public response: string;

  constructor(response: string) {
    this.response = response;
  }
}
