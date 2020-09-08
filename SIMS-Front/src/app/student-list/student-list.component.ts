import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {NotifierService} from 'angular-notifier';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginServiceService} from '../login-service.service';
import {StudentDto} from '../models/StudentDto';
import {GroupDto} from '../models/GroupDto';
import {GroupApplicationDto} from '../models/GroupApplicationDto';
import {DatePipe} from '@angular/common';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {isNull, isUndefined} from 'util';
import {EditCommentDialogComponent} from '../edit-comment-dialog/edit-comment-dialog.component';
import {isEmpty} from 'rxjs/operators';
import {PagerService} from '../pager-service.service';
import {PodsumowanieTrescDialogComponent} from '../podsumowanie-tresc-dialog/podsumowanie-tresc-dialog.component';
import {HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css'],
  providers: [DatePipe]
})
export class StudentListComponent implements OnInit {

  constructor(private fb: FormBuilder, notifierService: NotifierService, private activatedroute: ActivatedRoute,
              private authService: LoginServiceService, private datePipe: DatePipe, public dialog: MatDialog,
              private router: Router, private pagerService: PagerService) {
    this.notifier = notifierService;


  }


  group: GroupDto = {
    id: 0,
    groupName: '',
    durationInWeeks: 0,
    startDate: '',
    isOpen: false,
    fieldOfStudy: '',
    formOfStudy: '',
    speciality: '',
    changed: false,
    students: [],
    groupAdminId: 0,
    groupAdminName: '',
    groupAdminSurname: '',
    groupAdminEmail: ''
  };
  studentList = new Array<StudentDto>();
  savedstudentList = new Array<StudentDto>();


  studentApplicationList = new Array<GroupApplicationDto>();
  private readonly notifier: NotifierService;
  isAdmin = false;
  pager: any = {};
  pagedItems: any[];
  rememberSort = 'surname';
  show: boolean;

  sortOrderSurname = true;

  sortOrderCompanyName = true;

  foilterSurname = '';


  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
    this.load();
  }

  setPage(page: number) {
    // get pager object from service
    this.pager = this.pagerService.getPager(this.studentList.length, page);

    // get current page of items
    this.pagedItems = this.studentList.slice(this.pager.startIndex, this.pager.endIndex + 1);
  }

  load() {
    if (this.isAdmin) {
      let groupId: number;
      this.activatedroute.queryParams.subscribe(v =>
        groupId = v.groupId
      );

      this.authService.getResource('http://localhost:8080/api/group/' + groupId).subscribe(
        value => {
          // this.studentList.push(value.students);
          this.studentList = value.students;
          this.savedstudentList = value.students;
          this.setPage(this.pager.currentPage);
          // do student companyName
          console.log(value);


          this.group = value;


          if (this.rememberSort == 'surname') {
            this.sortOrderSurname = !this.sortOrderSurname;
            this.sortSurname();
          } else if (this.rememberSort == 'companyname') {
            this.sortOrderCompanyName = !this.sortOrderCompanyName;
            this.sortCompanyName();
          }

        },
        error => console.log(error),
      );

      this.authService.getResource('http://localhost:8080/api/group/' + groupId + '/applications').subscribe(
        value => {
          this.studentApplicationList = value;
          this.studentApplicationList.forEach(v => {
            v.date = this.datePipe.transform(v.date, 'yyyy-MM-dd').toString();
          });
        },
        error => console.log(error),
      );


    }
  }


  onAcceptClick(path: string) {
    this.authService.postResource('http://localhost:8080' + path, {}).subscribe(
      value => {
        this.load();
        this.notifier.notify('success', 'Pomyślnie zaakceptowano studenta',);
      },
      error => {
        console.log(error);
        this.notifier.notify('error', 'Coś poszło nie tak',);
      },
    );


  }

  onDeclineClick(path: string) {

    this.authService.postResource('http://localhost:8080' + path, {}).subscribe(
      value => {
        this.load();
        this.notifier.notify('success', 'Pomyślnie odrzucono studenta');
      },
      error => {
        console.log(error);
        this.notifier.notify('error', 'Coś poszło nie tak');
      },
    );


  }

  openDoc(id: number, docType: string) {
    if (docType == 'ankieta_studenta') {
      docType = 'ankietastudent';
    }
    else if (docType == 'ankieta_pracownik') {
      docType = 'ankietaprac';
    }

    this.router.navigate(['/' + docType], {queryParams: {id}});
  }

  removeFile(id: number) {
    const c = confirm('Czy na pewno chcesz usunąć ten dokument?');

    if (c) {
      this.authService.postResource('http://localhost:8080/delete/' + id, {}).subscribe(
        value => {
          this.notifier.notify('success', 'Pomyślnie usunięto plik');
          this.load();
        },
        error => {
          console.log(error);
          this.notifier.notify('error', error.error,);
        }
      );
    }
  }

  downloadDoc(id: number, docType: string, name: string, surname: string) {

    this.authService.download('http://localhost:8080/file/' + id).subscribe(
      (value) => {

        var fileExtension = value.headers.get('content-disposition').slice(21);
        //this.myDocumentList = value;
        // console.log(value);
        const objectURL = window.URL.createObjectURL(value.body);
        // window.open(objectURL, '_blank');
        // console.log(objectURL);
        var link = document.createElement('a');
        link.href = objectURL;
        link.download = docType + '_' + name + '_' + surname + '_' + this.group.groupName + '.' + fileExtension; //domyslna nazwa pliku
        link.click();
        setTimeout(function() {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(objectURL);
        }, 100);
      },

      error => console.log(error),
    );
  }

  groupSummaryButton() {

    if (this.isAdmin) {

      var message = '';
      const dialogRef = this.dialog.open(PodsumowanieTrescDialogComponent, {
        width: '800px',
        data: message,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          this.authService.postResource2('http://localhost:8080/api/group/summary/' + this.group.id, {text: result}).subscribe(
            value => {
              //this.myDocumentList = value;
              // console.log(value);
              const objectURL = window.URL.createObjectURL(value);
              // window.open(objectURL, '_blank');
              //console.log(objectURL);
              var link = document.createElement('a');
              link.href = objectURL;
              link.download = 'PodsumowanieGrupy ' + this.group.groupName + '.pdf'; //domyslna nazwa pliku
              link.click();
              setTimeout(function() {
                // For Firefox it is necessary to delay revoking the ObjectURL
                window.URL.revokeObjectURL(objectURL);
              }, 100);
            },
            error => console.log(error),
          );
        }
      });
    }
  }

  showWarning(message: string, id: number, docType: string) {
    if (this.isAdmin) {
      const dialogRef = this.dialog.open(EditCommentDialogComponent, {
        width: '400px',
        data: message,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          this.authService.postResource('http://localhost:8080/api/document/' + docType + '/' + id + '/comment', result).subscribe(
            value => {
              console.log(value);
              this.notifier.notify('success', 'Pomyślnie zmieniono uwage',);
              this.load();
            },
            error => {
              console.log(error);
              this.notifier.notify('error', error.error,);
            }
          );
        }
      });
    }

  }

  sortSurname() {
    if (this.sortOrderSurname) {
      this.studentList.sort((a: StudentDto, b: StudentDto) => b.surname.localeCompare(a.surname));
    } else {
      this.studentList.sort((a: StudentDto, b: StudentDto) => a.surname.localeCompare(b.surname));
    }
    this.sortOrderSurname = !this.sortOrderSurname;
    this.pagedItems = this.studentList.slice(this.pager.startIndex, this.pager.endIndex + 1);
    this.rememberSort = 'surname';
  }

  sortCompanyName() {
    if (this.sortOrderCompanyName) {
      this.studentList.sort((a: StudentDto, b: StudentDto) => b.companyName.localeCompare(a.companyName));
    } else {
      this.studentList.sort((a: StudentDto, b: StudentDto) => a.companyName.localeCompare(b.companyName));
    }
    this.sortOrderCompanyName = !this.sortOrderCompanyName;
    this.pagedItems = this.studentList.slice(this.pager.startIndex, this.pager.endIndex + 1);
    this.rememberSort = 'companyname';
  }

  findSurname() {
    if (this.foilterSurname == '') {
      this.studentList = this.savedstudentList;
      this.setPage(1);
      return;
    }
    this.studentList = this.savedstudentList.filter(v =>
      v.surname.includes(this.foilterSurname)
    );
    this.setPage(1);
  }

  changeDocuments(studentId) {

    this.authService.postResource('http://localhost:8080/api/group/' + this.group.id + '/users/job/' + studentId, {}).subscribe(
      value => {
        this.load();
        this.notifier.notify('success', 'Pomyślnie zmieniono dokumenty studenta');
      },
      error => {
        console.log(error);
        this.notifier.notify('error', 'Coś poszło nie tak');
      }
    );


  }

  dropStudent(studentId) {
    const c = confirm('Czy na pewno chcesz usunąć studenta z grupy?');

    if (c) {
      this.authService.postResource('http://localhost:8080/api/group/' + this.group.id + '/users/drop/' + studentId, {}).subscribe(
        value => {
          this.load();
          this.notifier.notify('success', 'Pomyślnie wyrzucono studenta',);
        },
        error => {
          console.log(error);
          this.notifier.notify('error', 'Coś poszło nie tak',);
        }
      );
    }


  }


  showAction(studentId) {
    if (document.getElementById(studentId).style.display === 'none') {
      document.getElementById(studentId).removeAttribute('style');
    } else {
      document.getElementById(studentId).style.display = 'none';
    }
  }


}
