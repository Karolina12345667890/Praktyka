import {Component, OnInit} from '@angular/core';
import {LoginServiceService} from '../login-service.service';
import {DocumentDto} from '../models/DocumentDto';

@Component({
  selector: 'app-home-component',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  private myDocumentList = new Array<DocumentDto>();


  constructor(private authService: LoginServiceService) {
  }

  ngOnInit() {
    if(this.authService.isLoggedIn()) {
      this.authService.getResource('http://localhost:8080/test123').subscribe(
        value => {
          this.myDocumentList = value;
          //  console.log(value);
        },
        error => console.log(error),
      );
    }

  }

  test123() {
    this.authService.getResource('http://localhost:8080/test12').subscribe(
      value => this.myDocumentList = value,
      error => console.log(error),
      () => console.log('error')
    );
  }

  test1234() {
    this.authService.postResource('http://localhost:8080/api/group/1/applications/new', {}).subscribe(
      value => console.log(value),
      error => alert(error.error)
    );
  }

  test12345() {
    this.authService.postResource('http://localhost:8080/api/group/1/applications/3', {}).subscribe(
      value => console.log(value),
      error => console.log(error)
    );
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

  showWarning(warning:string){
    alert( warning );

  }

}



export class Response {
  public response: string;

  constructor(response: string) {
    this.response = response;
  }
}
