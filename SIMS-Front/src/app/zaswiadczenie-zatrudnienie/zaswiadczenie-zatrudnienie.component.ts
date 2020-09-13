import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {LoginServiceService} from "../login-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NotifierService} from "angular-notifier";
import {MatDialog} from "@angular/material/dialog";
import {isNull, isUndefined} from "util";
import {DatePipe} from "@angular/common";
import {EditCommentDialogComponent} from "../edit-comment-dialog/edit-comment-dialog.component";
import {ZaswiadczenieZatrudnienieDto} from "../models/zaswiadczenieZatrudnienieDto";

@Component({
  selector: 'app-zaswiadczenie-zatrudnienie',
  templateUrl: './zaswiadczenie-zatrudnienie.component.html',
  styleUrls: ['./zaswiadczenie-zatrudnienie.component.css'],
  providers: [DatePipe]
})
export class ZaswiadczenieZatrudnienieComponent implements OnInit {

  zaswZatrudnienieForm: FormGroup;
  zaswZatrudnienie: ZaswiadczenieZatrudnienieDto;
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

    this.zaswZatrudnienieForm = this.fb.group({
      studentName: new FormControl('', Validators.required),
      studentSurname: new FormControl('', Validators.required),
      studentRoad: new FormControl('', Validators.required),
      studentCity: new FormControl('', Validators.required),
      studentZip: new FormControl('', Validators.required),
      studentPesel: new FormControl('', Validators.required),
      studentInternshipStart: new FormControl('', Validators.required),
      studentInternshipEnd: new FormControl('', Validators.required),
      companyName: new FormControl('', Validators.required),
      studentPosition: new FormControl('', Validators.required),
      hoursPerWeek: new FormControl(0, Validators.required),
      studentTasks: new FormControl('', Validators.required)
    });

    this.load();
  }

  private load() {
    let id: number;
    this.activatedroute.queryParams.subscribe(v =>
      id = v.id
    );

    this.authService.getResource('http://localhost:8080/api/document/zaswiadczeniezatrudnienie/' + id).subscribe(
      value => {
        this.zaswZatrudnienie = value;
        this.id = value.id;
        this.comment = value.comment;
        this.status = value.status;
        this.zaswZatrudnienieForm = new FormGroup({
          studentName: new FormControl('', Validators.required),
          studentSurname: new FormControl('', Validators.required),
          studentRoad: new FormControl(value.studentRoad, Validators.required),
          studentCity: new FormControl(value.studentCity, Validators.required),
          studentZip: new FormControl(value.studentZip, Validators.required),
          studentPesel: new FormControl(value.studentPesel, Validators.required),
          studentInternshipStart: new FormControl('', Validators.required),
          studentInternshipEnd: new FormControl('', Validators.required),
          companyName: new FormControl(value.companyName, [Validators.required]),
          studentPosition: new FormControl(value.studentPosition, [Validators.required]),
          hoursPerWeek: new FormControl(value.hoursPerWeek, [Validators.required]),
          studentTasks: new FormControl(value.studentTasks, [Validators.required])
        });

        if (!isNull(value.studentInternshipStart)) {
          this.sisInp = true;
          this.zaswZatrudnienieForm.get("studentInternshipStart").setValue(this.datePipe.transform(value.studentInternshipStart, 'yyyy-MM-dd').toString());
        }
        if (!isNull(value.studentInternshipEnd)) {
          this.sieInp = true;
          this.zaswZatrudnienieForm.get("studentInternshipEnd").setValue(this.datePipe.transform(value.studentInternshipEnd, 'yyyy-MM-dd').toString())
        }

        this.authService.getResource('http://localhost:8080/api/user/' + value.ownerId).subscribe(
          value => {
            this.zaswZatrudnienieForm.get("studentName").setValue(value.name);
            this.zaswZatrudnienieForm.get("studentSurname").setValue(value.surname);
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
    let body: ZaswiadczenieZatrudnienieDto = {
      studentRoad: this.zaswZatrudnienieForm.value.studentRoad,
      studentCity: this.zaswZatrudnienieForm.value.studentCity,
      studentZip: this.zaswZatrudnienieForm.value.studentZip,
      studentPesel: this.zaswZatrudnienieForm.value.studentPesel,
      studentInternshipStart: this.zaswZatrudnienieForm.value.studentInternshipStart,
      studentInternshipEnd: this.zaswZatrudnienieForm.value.studentInternshipEnd,
      companyName: this.zaswZatrudnienieForm.value.companyName,
      studentPosition: this.zaswZatrudnienieForm.value.studentPosition,
      hoursPerWeek: this.zaswZatrudnienieForm.value.hoursPerWeek,
      studentTasks: this.zaswZatrudnienieForm.value.studentTasks
    };

    this.authService.postResource('http://localhost:8080/api/document/zaswiadczeniezatrudnienie/' + this.id, body).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie wysłano dokument Zaświadczenie o zatrudnieniu",)
        this.router.navigate(["/home"]);
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
  }


  accept() {
    this.authService.postResource('http://localhost:8080/api/document/zaswiadczeniezatrudnienie/'+ this.id +'/accept', {}).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie zaakceptowano dokument Zaświadczenie o zatrudnieniu",);
        this.load();
      },
      error => {
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
    console.log('accept action')
  }


  decline() {
    this.authService.postResource('http://localhost:8080/api/document/zaswiadczeniezatrudnienie/'+ this.id +'/decline', {}).subscribe(
      value => {
        this.notifier.notify("success","Pomyślnie odrzucono dokument Zaświadczenie o zatrudnieniu",);
        this.load();
      },
      error =>{
        console.log(error)
        this.notifier.notify("error", error.error)
      }
    );
    console.log('decline action')
  }


  warning() {
    if(this.isAdmin) {
      const dialogRef = this.dialog.open(EditCommentDialogComponent, {
        width: '400px',
        data: this.comment,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          this.authService.postResource('http://localhost:8080/api/document/zaswiadczeniezatrudnienie/'+ this.id +'/comment', result).subscribe(
            value => {
              this.notifier.notify("success","Pomyślnie dodano uwagę");
              this.decline();
            },
            error =>{
              console.log(error)
              this.notifier.notify("error",error.error)
            }
          );
          console.log('warning action')
        }
      });
    }
  }


  check() {
    if(this.zaswZatrudnienieForm.invalid) {
      alert("Musisz uzupełnić wszystkie wymagane pola!");
    }
  }

}
