import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {LoginServiceService} from '../login-service.service';
import {DocumentDto} from '../models/DocumentDto';
import {isUndefined} from "util";
import {MatDialog} from "@angular/material/dialog";
import {ShowCommentDialogComponent} from "../show-comment-dialog/show-comment-dialog.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-component',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  private myDocumentList = new Array<DocumentDto>();

  @ViewChildren('groupTable') groupTables: QueryList<ElementRef>;
  myGroupedDocuments: Map<Array<any>,any> = new Map<Array<any>,any>();
  myGroups: Array<any> = new Array<any>();
  myGroupsStatus: Array<any> = new Array<any>();
  isAdmin: boolean = false;

  constructor(private authService: LoginServiceService, public dialog: MatDialog,private router: Router) {
  }

  ngOnInit() {
    if(this.authService.isLoggedIn()) {
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
          this.myGroups = Array.from(this.myGroupedDocuments.keys())
          this.myGroups = this.myGroups.sort((a,b) => {
            return a - b;
          })

          this.myGroups.forEach(groupId => {
            this.authService.getResource('http://localhost:8080/api/group/' + groupId).subscribe(
              value => {
                this.myGroupsStatus.push({
                  id: value.id,
                  name: value.groupName,
                  isOpen: value.isOpen
                })
              },
              error => console.log(error),
            );
          })
        },
        error => console.log(error),
      );
    }

    this.isAdmin = this.authService.isAdmin();
    if(this.isAdmin){
      this.router.navigate(['/gl']);
    }

  }


  openDoc(id:number,docType:string) {
    if(docType == 'planpraktyki'){
// cały ten if jest do testów i do usunięcia
      let body = {
        studentInternshipStart : '2020-01-01',
        studentInternshipEnd : '2020-01-05',
        studentTasks : 'aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa  aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa  aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa aaaaaaa aaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaa',
        studentPesel : '123456789',
      };
      console.log(body);
      this.authService.postResource('http://localhost:8080/api/document/planpraktyki/'+id, body).subscribe(
        value => {console.log(value)
        },
        error =>{ console.log(error)
        }
      );
    }else
// to zostaje
   this.router.navigate(['/'+docType], {queryParams: {id: id}});

  }

  onClick(path:string,documentType:string){
    this.authService.getResource2('http://localhost:8080'+path+'/pdf').subscribe(
      value => {
        this.myDocumentList = value;
        console.log(value);
        const objectURL = window.URL.createObjectURL(value);
        // window.open(objectURL, '_blank');
        console.log(objectURL);
        var link = document.createElement('a');
        link.href = objectURL;
        link.download = documentType+'.pdf'; //domyslna nazwa pliku
        link.click();
        setTimeout(function() {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(objectURL);
        }, 100);
      },
      error => console.log(error),
    );
  }


  showWarning(message: string) {
      const dialogRef = this.dialog.open(ShowCommentDialogComponent, {
        width: '400px',
        data: message,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (!isUndefined(result)) {
          //zmiana komentarza do dokumentu
          alert(result);
        }
      });
  }

  showHideGroup(element) {
    const groupTables = this.groupTables.toArray();

    groupTables.forEach(groupTable => {
      const grpTable = groupTable.nativeElement;
      if (grpTable.id == element) {
        if (grpTable.style.display === 'none') {
          grpTable.removeAttribute("style");
        }
        else {
          grpTable.style.display = 'none';
        }
      }
    })

  }


}



export class Response {
  public response: string;

  constructor(response: string) {
    this.response = response;
  }
}
