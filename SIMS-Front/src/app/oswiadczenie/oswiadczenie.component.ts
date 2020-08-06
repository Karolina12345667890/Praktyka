import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {oswiadczenieDto} from '../models/oswiadczenieDto';
import {ActivatedRoute, Router} from "@angular/router";
import {LoginServiceService} from "../login-service.service";
import {NotifierService} from "angular-notifier";
import {DatePipe} from "@angular/common";
import {MatDialog} from "@angular/material/dialog";
import {EditCommentDialogComponent} from "../edit-comment-dialog/edit-comment-dialog.component";
import {isUndefined} from "util";
// import {OAuthService} from

@Component({
  selector: 'app-oswiadczenie',
  templateUrl: './oswiadczenie.component.html',
  styleUrls: ['./oswiadczenie.component.css']
})
export class OswiadczenieComponent implements OnInit {

  isAdmin:boolean = false;
  id : number;
  comment : string;
  status : string = "";

  private readonly notifier: NotifierService;
  oswiadczenieForm:FormGroup;
  oswiadczenie: oswiadczenieDto;
  SERVER_URL = 'http://localhost:8080/api/document/99';
  visibility = false;

  // wstrzykuje zależności niezbedne servisy do działania componentu
  constructor(private fb: FormBuilder,
              private httpClient: HttpClient, private authService: LoginServiceService,
              private activatedroute: ActivatedRoute, notifierService: NotifierService,
              private router: Router , public dialog: MatDialog) {
    this.notifier = notifierService;

    this.oswiadczenieForm = this.fb.group({
      studentName: new FormControl('', [Validators.required,]),
      studentSurname: new FormControl('', [Validators.required,]),
      studentDuties: new FormControl('', [Validators.required,]),
      opiekunI: new FormControl('', [Validators.required,]),
      opiekunN: new FormControl('', [Validators.required,]),
      opiekunTel: new FormControl('', [Validators.required,]),
      opiekunMail: new FormControl('', [Validators.required,]),
    });

  this.load();
  }

  load(){
    let id: number;
    this.activatedroute.queryParams.subscribe(v =>
      id = v.id
    );

    this.authService.getResource('http://localhost:8080/api/document/oswiadczenie/'+id).subscribe(
      value => {
        this.oswiadczenie = value;
        this.id = value.id;
        this.comment = value.comment;
        this.status = value.status;
        this.oswiadczenieForm = this.fb.group({
          studentName: new FormControl('', [Validators.required,]),
          studentSurname: new FormControl('', [Validators.required,]),
          studentDuties: new FormControl(value.studentDuties, [Validators.required,]),
          opiekunI: new FormControl(value.opiekunI, [Validators.required,]),
          opiekunN: new FormControl(value.opiekunN, [Validators.required,]),
          opiekunTel: new FormControl(value.opiekunTel, [Validators.required,]),
          opiekunMail: new FormControl(value.opiekunMail, [Validators.required,]),
        });

        this.authService.getResource('http://localhost:8080/api/user/'+value.ownerId).subscribe(
          value => {
            this.oswiadczenieForm.get("studentName").setValue(value.name);
            this.oswiadczenieForm.get("studentSurname").setValue(value.surname);
            console.log(value);
          },
          error => console.log(error),
        );


        console.log(value);
      },
      error => console.log(error),
    );
  }

  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
  }

// metoda wysyłająca nasz obiekt oswiadczenieForm na server
  onSubmit() {

    let body: oswiadczenieDto = {
      opiekunI : this.oswiadczenieForm.value.opiekunI,
     opiekunN :this.oswiadczenieForm.value.opiekunN,
    opiekunTel : this.oswiadczenieForm.value.opiekunTel,
   opiekunMail : this.oswiadczenieForm.value.opiekunMail,
    studentDuties : this.oswiadczenieForm.value.studentDuties,
    };


    console.log(body);
    this.authService.postResource('http://localhost:8080/api/document/oswiadczenie/'+this.id, body).subscribe(
      value => {console.log(value)
        this.notifier.notify("success","Pomyślnie wysłano dokument Oświadczenie",)
        this.router.navigate(["/home"]);
      },
      error =>{ console.log(error)
        this.notifier.notify("error",error.error,)
      }
    );

    // this.oswiadczenieForm.value
    // const dataFromForm = this.oswiadczenieForm.value;
    // const dto: OswiadczenieDto = new OswiadczenieDto(
    //   null,
    //   dataFromForm.carerName,
    //   dataFromForm.carerSurname,
    //   dataFromForm.carerEmail,
    //   dataFromForm.carerPhone,
    // );

    // const headers = new HttpHeaders();
    // headers.append('Accept', 'application/json');
    // // headers.append('Content-Type', 'application/pdf');
    // headers.append('Document-Type', 'Oswiadczenie');
    // this.httpClient.post<Array<OswiadczenieDto>>(this.SERVER_URL, dto, {headers}).subscribe(
    //   value => console.log(value[value.length - 1]),
    //   error => console.log(error),
    //   () => console.log('3'),
    // );


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

  accept(){
    this.authService.postResource('http://localhost:8080/api/document/oswiadczenie/'+this.id+'/accept', {}).subscribe(
      value => { console.log(value);
        this.notifier.notify("success","Pomyślnie zaakceptowano dokument Oświadczenie",);
        this.load();
      },
      error =>{ console.log(error)
        this.notifier.notify("error",error.error,)
      }
    );
  }
  decline(){
    this.authService.postResource('http://localhost:8080/api/document/oswiadczenie/'+this.id+'/decline', {}).subscribe(
      value => { console.log(value)
        this.notifier.notify("success","Pomyślnie odrzucono dokument Oświadczenie",);
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
          this.authService.postResource('http://localhost:8080/api/document/oswiadczenie/'+this.id+'/comment', result).subscribe(
            value => { console.log(value);
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
    if(this.oswiadczenieForm.invalid){
      alert("disabled");
    }
  }




}
