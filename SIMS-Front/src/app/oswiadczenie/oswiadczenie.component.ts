import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {OswiadczenieDto} from './oswiadczenieDto';
// import {OAuthService} from

@Component({
  selector: 'app-oswiadczenie',
  templateUrl: './oswiadczenie.component.html',
  styleUrls: ['./oswiadczenie.component.css']
})
export class OswiadczenieComponent implements OnInit {

  oswiadczenieForm;
  SERVER_URL = 'http://localhost:8080/api/document/99';
  visibility = false;

  // wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private fb: FormBuilder,
              private httpClient: HttpClient) {
// tworzy wypełniony oswiadczenieFor w celu łatwiejszego testowania
    this.oswiadczenieForm = this.fb.group({
      studentName: new FormControl('', [Validators.required,]),
      studentSurname: new FormControl('', [Validators.required,]),
      studentDuties: new FormControl('', [Validators.required,]),
      carerName: new FormControl('', [Validators.required,]),
      carerSurname: new FormControl('', [Validators.required,]),
      carerPhone: new FormControl('', [Validators.required,]),
      carerEmail: new FormControl('', [Validators.required,]),
    });
  }


  ngOnInit() {
  }

// metoda wysyłająca nasz obiekt oswiadczenieForm na server
  onSubmit() {

    // this.oswiadczenieForm.value
    const dataFromForm = this.oswiadczenieForm.value;
    const dto: OswiadczenieDto = new OswiadczenieDto(
      null,
      dataFromForm.carerName,
      dataFromForm.carerSurname,
      dataFromForm.carerEmail,
      dataFromForm.carerPhone,
    );

    const headers = new HttpHeaders();
    headers.append('Accept', 'application/json');
    // headers.append('Content-Type', 'application/pdf');
    headers.append('Document-Type', 'Oswiadczenie');
    this.httpClient.post<Array<OswiadczenieDto>>(this.SERVER_URL, dto, {headers}).subscribe(
      value => console.log(value[value.length - 1]),
      error => console.log(error),
      () => console.log('3'),
    );


    // console.log(this.oswiadczenieForm.value);

    //
    // const data = JSON.stringify(this.oswiadczenieForm.value);
    // const headers = new HttpHeaders();
    // headers.append('Accept', 'application/pdf');
    // headers.append('Content-Type', 'application/pdf');
    // this.httpClient.post<any>(
    //   this.SERVER_URL, data, {headers}).subscribe(
    //   res => {
    //     this.visibility = true;
    //     console.log('sukces');
    //     // const fileURL = URL.createObjectURL(res);
    //     // window.open("http://localhost:8080/pdf123", '_blank');
    //   },
    //   (err) => {
    //     console.log(err);
    //     window.open('http://localhost:8080/pdf123', '_blank');
    //   },
    //   () => {
    //     console.log('3');
    //     this.visibility = true;
    //   }
    // );
  }

  check(){
    if(this.oswiadczenieForm.invalid){
      alert("disabled");
    }
  }

}
