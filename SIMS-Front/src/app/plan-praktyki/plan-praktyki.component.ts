import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {LoginServiceService} from "../login-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NotifierService} from "angular-notifier";
import {MatDialog} from "@angular/material/dialog";
import {planPraktykiDto} from "../models/planPraktykiDto";
import {isNull, isUndefined} from "util";
import {DatePipe} from "@angular/common";
import {EditCommentDialogComponent} from "../edit-comment-dialog/edit-comment-dialog.component";

@Component({
  selector: 'app-plan-praktyki',
  templateUrl: './plan-praktyki.component.html',
  styleUrls: ['./plan-praktyki.component.css'],
  providers: [DatePipe]
})
export class PlanPraktykiComponent implements OnInit {

  planPraktykiForm: FormGroup;
  planPraktyki: planPraktykiDto;
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

    this.planPraktykiForm = this.fb.group({
      studentName: new FormControl('', [Validators.required]),
      studentSurname: new FormControl('', [Validators.required]),
      studentInternshipStart: new FormControl('', [Validators.required]),
      studentInternshipEnd: new FormControl('', [Validators.required]),
      studentTasks: new FormControl('', [Validators.required]),
      studentPesel: new FormControl('', [Validators.required])
    });

    this.load();
  }

  private load() {
    let id: number;
    this.activatedroute.queryParams.subscribe(v =>
      id = v.id
    );

    this.authService.getResource('http://localhost:8080/api/document/planpraktyki/' + id).subscribe(
      value => {
        this.planPraktyki = value;
        this.id = value.id;
        this.comment = value.comment;
        this.status = value.status;
        this.planPraktykiForm = new FormGroup({
          studentName: new FormControl('', Validators.required),
          studentSurname: new FormControl('', Validators.required),
          studentInternshipStart: new FormControl('', Validators.required),
          studentInternshipEnd: new FormControl('', Validators.required),
          studentPesel: new FormControl(value.studentPesel, Validators.required),
          studentTasks: new FormControl(value.studentTasks, Validators.required)
        });

        if (!isNull(value.studentInternshipStart)) {
          this.sisInp = true;
          this.planPraktykiForm.get("studentInternshipStart").setValue(this.datePipe.transform(value.studentInternshipStart, 'yyyy-MM-dd').toString());
        }
        if (!isNull(value.studentInternshipEnd)) {
          this.sieInp = true;
          this.planPraktykiForm.get("studentInternshipEnd").setValue(this.datePipe.transform(value.studentInternshipEnd, 'yyyy-MM-dd').toString())
        }

        this.authService.getResource('http://localhost:8080/api/user/' + value.ownerId).subscribe(
          value => {
            this.planPraktykiForm.get("studentName").setValue(value.name);
            this.planPraktykiForm.get("studentSurname").setValue(value.surname);
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
    let body: planPraktykiDto = {
      studentInternshipStart: this.planPraktykiForm.value.studentInternshipStart,
      studentInternshipEnd: this.planPraktykiForm.value.studentInternshipEnd,
      studentPesel: this.planPraktykiForm.value.studentPesel,
      studentTasks: this.planPraktykiForm.value.studentTasks
    };

    this.authService.postResource('http://localhost:8080/api/document/planpraktyki/' + this.id, body).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie wysłano dokument Plan praktyki zwodowej",)
        this.router.navigate(["/home"]);
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
  }


  accept() {
    this.authService.postResource('http://localhost:8080/api/document/planpraktyki/'+ this.id +'/accept', {}).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie zaakceptowano dokument Plan praktyki zawodowej",);
        this.load();
      },
      error => {
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
  }


  decline() {
    this.authService.postResource('http://localhost:8080/api/document/planpraktyki/'+ this.id +'/decline', {}).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie odrzucono dokument Plan praktyki zawodowej",);
        this.load();
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", error.error)
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
          this.authService.postResource('http://localhost:8080/api/document/planpraktyki/'+ this.id +'/comment', result).subscribe(
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
    if(this.planPraktykiForm.invalid) {
      alert("Musisz uzupełnić wszystkie wymagane pola!");
    }
  }


}
