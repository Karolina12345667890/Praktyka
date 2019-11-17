import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-porozumienie',
  templateUrl: './porozumienie.component.html',
  styleUrls: ['./porozumienie.component.css']
})
export class PorozumienieComponent implements OnInit {

  porozumienieForm;
  SERVER_URL = 'http://localhost:8080/pdfPost';
  visibility = false;

// wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private fb: FormBuilder,
              private httpClient: HttpClient) {
// tworzy wypełniony obiekt porozumienieForm w celu łatwiejszego testowania
    this.porozumienieForm = this.fb.group({
      companyName: new FormControl('', [Validators.required,]),
      companyLocationCity: new FormControl('', [Validators.required,]),
      companyLocationStreet: new FormControl('', [Validators.required,]),
      companyRepresentantName: new FormControl('', [Validators.required,]),
      companyRepresentantSurname: new FormControl('', [Validators.required,]),
      studentSpecialization: new FormControl('', [Validators.required,]),
      studentInternshipDuration: new FormControl('', [Validators.required,]),
      studentName: new FormControl('', [Validators.required,]),
      studentSurname: new FormControl('', [Validators.required,]),
      studentStudyForm: new FormControl('', [Validators.required,]),
      studentInternshipStart: new FormControl('', [Validators.required,]),
      studentInternshipEnd: new FormControl('', [Validators.required,]),
    });

  }

  ngOnInit() {
  }

  // metoda wysyłająca nasz obiekt porozumienieForm na server
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
