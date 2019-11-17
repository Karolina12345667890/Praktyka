import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-zaswiadczenie',
  templateUrl: './zaswiadczenie.component.html',
  styleUrls: ['./zaswiadczenie.component.css']
})
export class ZaswiadczenieComponent implements OnInit {

  zaswiadczenieForm;

  constructor(private fb: FormBuilder,
              private httpClient: HttpClient) {
    this.zaswiadczenieForm = this.fb.group({


      studentName: new FormControl('', [Validators.required,]),
      studentSurname: new FormControl('', [Validators.required,]),
      periodFrom: new FormControl('', [Validators.required,]),
      periodTo: new FormControl('', [Validators.required,]),
      studentRating1: new FormControl('', [Validators.required,]),
      studentRating2: new FormControl('', [Validators.required,]),
      studentRating3: new FormControl('', [Validators.required,]),
      studentRating: new FormControl('', [Validators.required,]),
      studentWorks: new FormControl('', [Validators.required,]),
      studentInterests: new FormControl('', [Validators.required,]),
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    console.log(this.zaswiadczenieForm);
  }
  check(){
    if(this.zaswiadczenieForm.invalid){
      alert("disabled");
    }
  }
}
