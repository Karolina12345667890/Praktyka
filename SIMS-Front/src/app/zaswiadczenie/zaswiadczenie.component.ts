import {Component, OnInit} from '@angular/core';
import {Form, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {LoginServiceService} from "../login-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {isNull, isUndefined} from "util";
import {planPraktykiDto} from "../models/planPraktykiDto";
import {EditCommentDialogComponent} from "../edit-comment-dialog/edit-comment-dialog.component";
import {DatePipe} from "@angular/common";
import {NotifierService} from "angular-notifier";
import {MatDialog} from "@angular/material/dialog";
import {ZaswiadczenieDto} from "../models/zaswiadczenieDto";

@Component({
  selector: 'app-zaswiadczenie',
  templateUrl: './zaswiadczenie.component.html',
  styleUrls: ['./zaswiadczenie.component.css']
})
export class ZaswiadczenieComponent implements OnInit {

  zaswiadczenieForm: FormGroup;
  zaswiadczenie: ZaswiadczenieDto;
  private readonly notifier: NotifierService;
  id: number;
  comment: string;
  status: string = "";
  isAdmin: boolean = false;
  sisInp: boolean = false;
  sieInp: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: LoginServiceService,
    private datePipe: DatePipe,
    private httpClient: HttpClient,
    private activatedroute: ActivatedRoute, notifierService: NotifierService,
    private router: Router,
    public dialog: MatDialog
  ) {

    this.notifier = notifierService;

    this.zaswiadczenieForm = this.fb.group({
      studentName: new FormControl('', [Validators.required]),
      studentSurname: new FormControl('', [Validators.required]),
      studentInternshipStart: new FormControl('', [Validators.required]),
      studentInternshipEnd: new FormControl('', [Validators.required]),
      studentRating1: new FormControl('', [Validators.required]),
      studentRating2: new FormControl('', [Validators.required]),
      studentRating3: new FormControl('', [Validators.required]),
      studentRating: new FormControl('', [Validators.required]),
      studentWorks: new FormControl('', [Validators.required]),
      studentInterests: new FormControl('', [Validators.required])
    });

    this.load();
  }

  private load() {
    let id: number;
    this.activatedroute.queryParams.subscribe(v =>
      id = v.id
    );

    this.authService.getResource('http://localhost:8080/api/document/zaswiadczenie/' + id).subscribe(
      value => {

        this.zaswiadczenieForm = value;
        this.id = value.id;
        this.comment = value.comment;
        this.status = value.status;
        this.zaswiadczenieForm = new FormGroup({
          studentName: new FormControl('', [Validators.required]),
          studentSurname: new FormControl('', [Validators.required]),
          studentInternshipStart: new FormControl('', [Validators.required]),
          studentInternshipEnd: new FormControl('', [Validators.required]),
          studentRating1: new FormControl(value.studentRating1, [Validators.required]),
          studentRating2: new FormControl(value.studentRating2, [Validators.required]),
          studentRating3: new FormControl(value.studentRating3, [Validators.required]),
          studentRating: new FormControl(value.studentRating, [Validators.required]),
          studentWorks: new FormControl(value.studentWorks, [Validators.required]),
          studentInterests: new FormControl(value.studentInterests, [Validators.required])
        });

        if (!isNull(value.studentInternshipStart)) {
          this.sisInp = true;
          this.zaswiadczenieForm.get("studentInternshipStart").setValue(this.datePipe.transform(value.studentInternshipStart, 'yyyy-MM-dd').toString());
        }
        if (!isNull(value.studentInternshipEnd)) {
          this.sieInp = true;
          this.zaswiadczenieForm.get("studentInternshipEnd").setValue(this.datePipe.transform(value.studentInternshipEnd, 'yyyy-MM-dd').toString())
        }

        this.authService.getResource('http://localhost:8080/api/user/' + value.ownerId).subscribe(
          value => {
            this.zaswiadczenieForm.get("studentName").setValue(value.name);
            this.zaswiadczenieForm.get("studentSurname").setValue(value.surname);
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
    let body: ZaswiadczenieDto = {
      studentInternshipStart: this.zaswiadczenieForm.value.studentInternshipStart,
      studentInternshipEnd: this.zaswiadczenieForm.value.studentInternshipEnd,
      studentRating1: this.zaswiadczenieForm.value.studentRating1,
      studentRating2: this.zaswiadczenieForm.value.studentRating2,
      studentRating3: this.zaswiadczenieForm.value.studentRating3,
      studentRating: this.zaswiadczenieForm.value.studentRating,
      studentWorks: this.zaswiadczenieForm.value.studentWorks,
      studentInterests: this.zaswiadczenieForm.value.studentInterests,
    };

    this.authService.postResource('http://localhost:8080/api/document/zaswiadczenie/' + this.id, body).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie wysłano dokument Zaświadczenie",)
        this.router.navigate(["/home"]);
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
  }


  accept() {
    this.authService.postResource('http://localhost:8080/api/document/zaswiadczenie/'+ this.id +'/accept', {}).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie zaakceptowano dokument Zaświadczenie",);
        this.load();
      },
      error => {
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
  }


  decline() {
    this.authService.postResource('http://localhost:8080/api/document/zaswiadczenie/'+ this.id +'/decline', {}).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie odrzucono dokument Zaświadczenie",);
        this.load();
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
  }


  warning() {
    if (this.isAdmin) {
      const dialogRef = this.dialog.open(EditCommentDialogComponent, {
        width: '400px',
        data: this.comment,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          this.authService.postResource('http://localhost:8080/api/document/zaswiadczenie/'+ this.id +'/comment', result).subscribe(
            value => {
              this.notifier.notify("success","Pomyślnie dodano uwagę");
              this.decline();
            },
            error =>{
              console.log(error)
              this.notifier.notify("error",error.error)
            }
          );
        }
      });
    }
  }


  check() {
    if(this.zaswiadczenieForm.invalid) {
      alert("Musisz uzupełnić wszystkie wymagane pola!");
    }
  }
}
