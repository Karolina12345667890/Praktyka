import {
  AfterContentInit,
  AfterViewInit,
  Component,
  ElementRef,
  OnChanges,
  OnInit,
  QueryList, SimpleChanges,
  ViewChildren
} from '@angular/core';
import {LoginServiceService} from '../login-service.service';
import {DocumentDto} from '../models/DocumentDto';
import {isUndefined} from 'util';
import {MatDialog} from '@angular/material/dialog';
import {ShowCommentDialogComponent} from '../show-comment-dialog/show-comment-dialog.component';
import {ActivatedRoute, Router} from '@angular/router';
import {FileUploadComponent} from '../file-upload/file-upload.component';

@Component({
  selector: 'app-home-component',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit, AfterViewInit {

  private myDocumentList = new Array<DocumentDto>();

  @ViewChildren('groupTable') groupTables: QueryList<ElementRef>;
  myGroupedDocuments: Map<Array<any>, any> = new Map<Array<any>, any>();
  myGroups: Array<any> = new Array<any>();
  myGroupsStatus: Array<any> = new Array<any>();
  isAdmin: boolean = false;

  constructor(private authService: LoginServiceService, public dialog: MatDialog, private router: Router) {
  }

  ngOnInit() {


    this.isAdmin = this.authService.isAdmin();
    if (this.isAdmin) {
      this.router.navigate(['/gl']);
    }

    this.load();
  }

  load(){
    if (this.authService.isLoggedIn()) {
      this.authService.getResource('http://localhost:8080/api/document/list').subscribe(
        value => {
          this.myDocumentList = value;

          function groupBy(list, keyGetter) {
            const map = new Map();
            list.forEach((item) => {
              const key = keyGetter(item);
              const collection = map.get(key);
              if (!collection) {
                map.set(key, [item]);
              } else {
                collection.push(item);
              }
            });
            return map;
          }

          this.myGroupedDocuments = groupBy(value, value => value.groupId);
          this.myGroups = Array.from(this.myGroupedDocuments.keys());

          this.myGroups.forEach(groupId => {
            this.authService.getResource('http://localhost:8080/api/group/' + groupId + '/overview').subscribe(
              value => {
                this.myGroupsStatus.push({
                  id: value.id,
                  name: value.groupName,
                  isOpen: value.isOpen,
                  groupAdminName: value.groupAdminName,
                  groupAdminSurname: value.groupAdminSurname,
                  groupAdminEmail: value.groupAdminEmail,
                  fieldOfStudy: value.fieldOfStudy,
                  speciality: value.speciality,
                  durationInWeeks: value.durationInWeeks
                });
              },
              error => console.log(error),
            );
          });
        },
        error => console.log(error),
      );
    }
  }
  ngAfterViewInit() {
    setTimeout(() => {
      this.myGroupsStatus.sort((a, b) => {
        return b.isOpen - a.isOpen;
      });
    }, 100);
  }


  openDoc(id: number, docType: string) {
    this.router.navigate(['/' + docType], {queryParams: {id: id}});
  }

  onClick(path: string, documentType: string) {
    this.authService.getResource2('http://localhost:8080' + path + '/pdf').subscribe(
      value => {
        this.myDocumentList = value;
       // console.log(value);
        const objectURL = window.URL.createObjectURL(value);
        // window.open(objectURL, '_blank');
        console.log(objectURL);
        var link = document.createElement('a');
        link.href = objectURL;
        link.download = documentType + '.pdf'; //domyslna nazwa pliku
        link.click();
        setTimeout(function() {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(objectURL);
        }, 100);
      },
      error => console.log(error),
    );
  }

  uploadDoc(id: number, docType: string, groupName: string) {
    const dialogRef = this.dialog.open(FileUploadComponent, {
      width: '800px',
      data: {docId: id, doctype: docType, groupname: groupName},
    });

  }

  openStudentQuestionnaire(groupId: string, groupSpeciality: string) {
    this.router.navigate(['/ankietastudent'], {queryParams: {id: groupId, s: groupSpeciality}});
  }

  openEmployerQuestionnaire(groupId: string) {
    this.router.navigate(['/ankietaprac'], {queryParams: {id: groupId}});
  }

  showWarning(message: string) {
    const dialogRef = this.dialog.open(ShowCommentDialogComponent, {
      width: '400px',
      data: message,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!isUndefined(result)) {

        window.location.reload(true);
      }
    });
  }

  showHideGroup(element) {
    const groupTables = this.groupTables.toArray();

    groupTables.forEach(groupTable => {
      const grpTable = groupTable.nativeElement;
      if (grpTable.id == element) {
        if (grpTable.style.display === 'none') {
          grpTable.removeAttribute('style');
        } else {
          grpTable.style.display = 'none';
        }
      }
    });

  }

  test6() {
    this.authService.getResource('http://localhost:8080/api/document/ankieta_studenta/1/summaryStudentSurvay').subscribe(value => console.log(value), error => console.log(error));
  }

  test7() {
    this.authService.getResource('http://localhost:8080/api/document/ankieta_pracownik/1/summaryPracownikSurvay').subscribe(value => console.log(value), error => console.log(error));
  }

}


export class Response {
  public response: string;

  constructor(response: string) {
    this.response = response;
  }
}
