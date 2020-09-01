import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AfterContentInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { NotifierService } from 'angular-notifier';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit, AfterContentInit {


  @ViewChild('inputFile', { static: true }) fileInput: ElementRef;
  @ViewChild('labelFileName', { static: true }) labelFileName: ElementRef;
  @ViewChild('docType', { static: false }) docType: ElementRef;

  SERVER_URL = 'http://localhost:8080/upload';
  uploadForm: FormGroup;
  private readonly notifier: NotifierService;

  selectedDocument: String = '';
  selectedGroup: String = '';

  constructor(private formBuilder: FormBuilder,
              private httpClient: HttpClient,
              notifierService: NotifierService, private currentRoute: ActivatedRoute) {
    this.notifier = notifierService;
  }

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      file: new FormControl(Array<File>(), Validators.required)
    });

    this.currentRoute.queryParams.subscribe(params => {
      if (params.group != undefined) {
        this.selectedGroup = params.group;
      }

      if (params.doc != undefined) {
        this.selectedDocument = params.doc;
      }
    })
  }


  ngAfterContentInit() {
    this.fileInput.nativeElement.addEventListener('change', () => {
      this.labelFileName.nativeElement.innerHTML = this.fileInput.nativeElement.files[0].name;
    })
  }


  onSubmit() {
    const formData = new FormData();
    formData.append('file', this.uploadForm.get('file').value);
    formData.append('documentType', this.docType.nativeElement.value);

    this.httpClient.post<any>(this.SERVER_URL, formData).subscribe(
      (res) => {console.log(res)
        this.notifier.notify( 'success', 'Pomyślnie wysłano: '+ this.uploadForm.get('documentType').value );
      },
      (err) => {console.log(err)
        this.notifier.notify( 'error', 'Wystąpił błąd przy wysyłaniu dokumentu: '+ this.uploadForm.get('documentType').value );
      }
    );
  }

}
