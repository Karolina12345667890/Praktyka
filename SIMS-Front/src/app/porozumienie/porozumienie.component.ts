import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ActivatedRoute, Router} from "@angular/router";
import {NotifierService} from "angular-notifier";
import {MatDialog} from "@angular/material/dialog";
import {LoginServiceService} from "../login-service.service";
import {porozumienieDto} from "../models/porozumienieDto";
import {EditCommentDialogComponent} from "../edit-comment-dialog/edit-comment-dialog.component";
import {isNull, isUndefined} from "util";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-porozumienie',
  templateUrl: './porozumienie.component.html',
  styleUrls: ['./porozumienie.component.css'],
  providers: [DatePipe]
})
export class PorozumienieComponent implements OnInit {

  porozumienieForm: FormGroup;
  SERVER_URL = 'http://localhost:8080/pdfPost';
  visibility = false;
  private readonly notifier: NotifierService;
  id: number;
  comment: string;
  status: string = "";
  isAdmin:boolean = false;

// wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private fb: FormBuilder, private authService: LoginServiceService, private datePipe: DatePipe,
              private httpClient: HttpClient,private activatedroute: ActivatedRoute, notifierService: NotifierService,
              private router: Router , public dialog: MatDialog) {

    this.notifier = notifierService;

    this.porozumienieForm = this.fb.group({
      companyName: new FormControl('', [Validators.required]),
      companyLocationCity: new FormControl('', [Validators.required]),
      companyLocationStreet: new FormControl('', [Validators.required]),
      companyRepresentantName: new FormControl('', [Validators.required]),
      companyRepresentantSurname: new FormControl('', [Validators.required]),
      studentSpecialization: new FormControl('', [Validators.required]),
      studentInternshipDuration: new FormControl('', [Validators.required]),
      studentName: new FormControl('', [Validators.required]),
      studentSurname: new FormControl('', [Validators.required]),
      studentStudyForm: new FormControl('', [Validators.required]),
      studentInternshipStart: new FormControl('', [Validators.required]),
      studentInternshipEnd: new FormControl('', [Validators.required]),
    });

    this.load();
  }


  load() {
    let id: number;
    this.activatedroute.queryParams.subscribe(v =>
      id = v.id
    );

    this.authService.getResource('http://localhost:8080/api/document/porozumienie/' + id).subscribe(
      value => {

        this.id = value.id;
        this.comment = value.comment;
        this.status = value.status;
        this.porozumienieForm = this.fb.group({
          companyName: new FormControl(value.companyName, [Validators.required]),
          companyLocationCity: new FormControl(value.companyLocationCity, [Validators.required]),
          companyLocationStreet: new FormControl(value.companyLocationStreet, [Validators.required]),
          companyRepresentantName: new FormControl(value.companyRepresentantName, [Validators.required]),
          companyRepresentantSurname: new FormControl(value. companyRepresentantSurname, [Validators.required]),
          studentSpecialization: new FormControl('', [Validators.required]),
          studentInternshipDuration: new FormControl('', [Validators.required]),
          studentName: new FormControl('', [Validators.required]),
          studentSurname: new FormControl('', [Validators.required]),
          studentStudyForm: new FormControl('', [Validators.required]),
          studentInternshipStart: new FormControl('', [Validators.required]),
          studentInternshipEnd: new FormControl('', [Validators.required]),
        });

        if (!isNull(value.studentInternshipStart)) {
          this.porozumienieForm.get("studentInternshipStart").setValue(this.datePipe.transform(value.studentInternshipStart, 'yyyy-MM-dd').toString());
        }
        if (!isNull(value.studentInternshipEnd)) {
          this.porozumienieForm.get("studentInternshipEnd").setValue(this.datePipe.transform(value.studentInternshipEnd, 'yyyy-MM-dd').toString());
        }

        this.authService.getResource('http://localhost:8080/api/group/'+value.groupId+'/overview').subscribe(
          value => {

            this.porozumienieForm.get("studentInternshipDuration").setValue(value.durationInWeeks);
            this.porozumienieForm.get("studentSpecialization").setValue(value.speciality);
            if(value.formOfStudy == "FULL_TIME")
              this.porozumienieForm.get("studentStudyForm").setValue('Stacjonarne');
            else
              this.porozumienieForm.get("studentStudyForm").setValue('Niestacjonarne');

          },
          error => console.log(error),
        );

        this.authService.getResource('http://localhost:8080/api/user/'+value.ownerId).subscribe(
          value => {
            this.porozumienieForm.get("studentName").setValue(value.name);
            this.porozumienieForm.get("studentSurname").setValue(value.surname);

          },
          error => console.log(error),
        );

      },
      error => console.log(error),
    );
  }


  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
  }


  onSubmit() {

    let body: porozumienieDto = {
      companyName: this.porozumienieForm.value.companyName,
    companyLocationCity: this.porozumienieForm.value.companyLocationCity,
    companyLocationStreet: this.porozumienieForm.value.companyLocationStreet,
    companyRepresentantName: this.porozumienieForm.value.companyRepresentantName,
    companyRepresentantSurname: this.porozumienieForm.value.companyRepresentantSurname,
    studentInternshipStart: this.porozumienieForm.value.studentInternshipStart,
    studentInternshipEnd: this.porozumienieForm.value.studentInternshipEnd,
      studentStudyForm: this.porozumienieForm.value.studentStudyForm,
      studentSpecialization: this.porozumienieForm.value.studentSpecialization
    };

    console.log(body);
    this.authService.postResource('http://localhost:8080/api/document/porozumienie/'+this.id, body).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie wysłano dokument Porozumienie",)
        this.router.navigate(["/home"]);
      },
      error =>{ console.log(error)
        this.notifier.notify("error",error.error,)
      }
    );
  }


  accept() {
    this.authService.postResource('http://localhost:8080/api/document/porozumienie/'+this.id+'/accept', {}).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie zaakceptowano dokument Porozumienie",);
        this.load();
      },
      error =>{ console.log(error)
        this.notifier.notify("error",error.error,)
      }
    );
  }


  decline(){
    this.authService.postResource('http://localhost:8080/api/document/porozumienie/'+this.id+'/decline', {}).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie odrzucono dokument Porozumienie",);
        this.load();
      },
      error =>{ console.log(error)
        this.notifier.notify("error",error.error,)
      }
    );
  }


  warning() {
    if(this.isAdmin) {
      const dialogRef = this.dialog.open(EditCommentDialogComponent, {
        width: '400px',
        data: this.comment,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          this.authService.postResource('http://localhost:8080/api/document/porozumienie/'+this.id+'/comment', result).subscribe(
            value => {
              this.notifier.notify("success","Pomyślnie dodano uwagę",);
              this.decline();
            },
            error =>{ console.log(error)
              this.notifier.notify("error",error.error,)
            }
          );
        }
      });
    }
  }


  check(){
    if(this.porozumienieForm.invalid){
      alert("disabled");
    }
  }
}
