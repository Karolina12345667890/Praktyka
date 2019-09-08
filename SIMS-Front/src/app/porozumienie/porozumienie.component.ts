import { Component, OnInit } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-porozumienie',
  templateUrl: './porozumienie.component.html',
  styleUrls: ['./porozumienie.component.css']
})
export class PorozumienieComponent implements OnInit {

  porozumienieForm;
  SERVER_URL = 'http://localhost:8080/pdfPost';
  visibility = false;

  constructor(private fb: FormBuilder,
              private httpClient: HttpClient) {

    this.porozumienieForm = this.fb.group({
      companyName: ['nazwa firmy sp. z o. o.'],
      companyLocationCity: ['Siedlce'],
      companyLocationStreet: ['3-go maja'],
      companyRepresentantName: ['ja'],
      companyRepresentantSurname: ['moje'],
      studentSpecialization: ['Informatyka'],
      studentInternshipDuration: ['4'],
      studentName: ['tomek'],
      studentSurname: ['romek'],
      studentStudyForm: ['Stacjonarne'],
      studentInternshipStart: ['01.01.2000'],
      studentInternshipEnd: ['01.02.2000'],
    });

  }

  ngOnInit() {
  }
  onSubmit() {
    console.log(this.porozumienieForm.value);

    const data = JSON.stringify(this.porozumienieForm.value);
    const headers = new HttpHeaders();
    headers.append('Accept', 'application/pdf');
    headers.append('Content-Type', 'application/pdf');

    this.httpClient.post<any>(
      this.SERVER_URL, data, {headers}).subscribe(
      res => {
        this.visibility = true;
        console.log('sukces');
        // const fileURL = URL.createObjectURL(res);
        // window.open("http://localhost:8080/pdf123", '_blank');
      },
      (err) => {
        console.log(err);
        window.open('http://localhost:8080/pdf123', '_blank');
      },
      () => {
        console.log('3');
        this.visibility = true;
      }
    );
  }
}
