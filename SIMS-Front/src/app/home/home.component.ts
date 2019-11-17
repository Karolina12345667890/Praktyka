import {Component, OnInit} from '@angular/core';
import {LoginServiceService} from '../login-service.service';

@Component({
  selector: 'app-home-component',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  private aaa = new Response('123');

  constructor(private authService: LoginServiceService) {


  }

  ngOnInit() {

  }

  test123() {
    this.authService.getResource('http://localhost:8080/test12').subscribe(
      value => this.aaa = value,
      error => console.log(error),
      () => console.log('error')
    );
  }

  test1234() {
    this.authService.getResource('http://localhost:8080/test123').subscribe(
      value => {
        this.aaa = value;
        console.log(value);
      },
      error => console.log(error),
    );
  }

  test12345() {
    this.authService.getResource2('http://localhost:8080/api/document/oswiadczenie/1/pdf').subscribe(
      value => {
        this.aaa = value;
        console.log(value);
        const objectURL = window.URL.createObjectURL(value);
        // window.open(objectURL, '_blank');
        console.log(objectURL);
        var link = document.createElement('a');
        link.href = objectURL;
        link.download = 'file.pdf'; //domyslna nazwa pliku
        link.click();
        setTimeout(function() {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(objectURL);
        }, 100);
      },
      error => console.log(error),
    );
  }

}

export class Response {
  public response: string;

  constructor(response: string) {
    this.response = response;
  }
}
