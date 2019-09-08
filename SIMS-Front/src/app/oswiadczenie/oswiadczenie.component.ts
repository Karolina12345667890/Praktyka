import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder} from '@angular/forms';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-oswiadczenie',
  templateUrl: './oswiadczenie.component.html',
  styleUrls: ['./oswiadczenie.component.css']
})
export class OswiadczenieComponent implements OnInit {

  oswiadczenieForm;
  SERVER_URL = 'http://localhost:8080/pdfPost';
  visibility = false;

  constructor(private fb: FormBuilder,
              private httpClient: HttpClient) {

    this.oswiadczenieForm = this.fb.group({
      studentName: ['tomek'],
      studentSurname: ['romek'],
      studentDuties: ['1,2,3,4'],
        //this.fb.array([
       // this.fb.control('c')
     // ]),
      carerName: ['ja'],
      carerSurname: ['moje'],
      carerPhone: ['123456789'],
      carerEmail: ['test@test.test'],
    });
  }

  get duties() {
    return this.oswiadczenieForm.get('studentDuties') as FormArray;
  }

  addDuty() {
    this.duties.push(this.fb.control(''));
  }

  ngOnInit() {
  }


  onSubmit() {
    console.log(this.oswiadczenieForm.value);

    const data = JSON.stringify(this.oswiadczenieForm.value);
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
