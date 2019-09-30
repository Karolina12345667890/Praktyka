import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-oswiadczenie',
  templateUrl: './oswiadczenie.component.html',
  styleUrls: ['./oswiadczenie.component.css']
})
export class OswiadczenieComponent implements OnInit {

  oswiadczenieForm;
  SERVER_URL = 'http://localhost:8080/api/document';
  visibility = false;

  // wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private fb: FormBuilder,
              private httpClient: HttpClient) {
// tworzy wypełniony oswiadczenieFor w celu łatwiejszego testowania
    this.oswiadczenieForm = this.fb.group({
      studentName: new FormControl('', [ Validators.required, ]),
      studentSurname: new FormControl('', [ Validators.required, ]),
      studentDuties: new FormControl('', [ Validators.required, ]),
      carerName: new FormControl('', [ Validators.required, ]),
      carerSurname: new FormControl('', [ Validators.required, ]),
      carerPhone: new FormControl('', [ Validators.required, ]),
      carerEmail: new FormControl('', [ Validators.required, ]),
    });
  }



  ngOnInit() {
  }

// metoda wysyłająca nasz obiekt oswiadczenieForm na server
  onSubmit() {

    console.log(this.oswiadczenieForm.value);

  }

  check(){
    if(this.oswiadczenieForm.invalid){
      alert("disabled");
    }
  }

}
