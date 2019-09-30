import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {

  studentListForm: FormGroup;
  studentList: FormArray;

  constructor(private fb: FormBuilder) {


    this.studentList= this.fb.array([]);
    this.fill();
    console.log(this.studentList)
  }


  //do testów
  fill(){
    this.studentList.push(
      this.studentListForm = this.fb.group({
        id: '1',
        studentName: 'Ala',
        studentSurname: 'Woj',
        studentSpecialization: 'Informatyka',
        periodFrom: '11.11.2001',
        periodTo: '11.12.2001',
        companyName:  'Sony Corporation',
        documentOswiadczenieStatus: 'New',
        documentPorozumienieStatus: '',
        documentZaswiadczenieStatus: 'Accepted',
        documentDziennikStatus: '',
      })
    );

    this.studentList.push(
      this.studentListForm = this.fb.group({
        id: '2',
        studentName: 'Tomek',
        studentSurname: 'Tar',
        studentSpecialization: 'Chemia',
        periodFrom: '11.11.2001',
        periodTo: '11.12.2001',
        companyName:  'mBank, Bankowość Detaliczna BRE Banku SA',
        documentOswiadczenieStatus: 'Declined',
        documentPorozumienieStatus: 'Accepted',
        documentZaswiadczenieStatus: 'Accepted',
        documentDziennikStatus: '',
      })
    );

    this.studentList.push(
      this.studentListForm = this.fb.group({
        id: '3',
        studentName: 'Andrzej',
        studentSurname: 'Nowosielski',
        studentSpecialization: 'Matematyka',
        periodFrom: '11.11.2001',
        periodTo: '11.12.2001',
        companyName:  'Uniwersytet Przyrodniczo Humanistyczny w Siedlcach wydział Nauk Ścisłych',
        documentOswiadczenieStatus: 'New',
        documentPorozumienieStatus: 'New',
        documentZaswiadczenieStatus: '',
        documentDziennikStatus: '',
      })
    )
  }

  onClick(id:number,document:string){
    console.log(id+" "+ document);
  }

  ngOnInit() {
  }

}
