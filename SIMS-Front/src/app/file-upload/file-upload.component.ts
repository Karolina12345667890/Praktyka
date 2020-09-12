import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AfterContentInit, Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpEventType, HttpResponse} from '@angular/common/http';
import {NotifierService} from 'angular-notifier';
import {ActivatedRoute} from '@angular/router';
import {LoginServiceService} from '../login-service.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit, AfterContentInit {


  @ViewChild('inputFile', {static: true}) fileInput: ElementRef;
  @ViewChild('labelFileName', {static: true}) labelFileName: ElementRef;
  @ViewChild('docType', {static: false}) docType: ElementRef;

  SERVER_URL = 'http://localhost:8080/upload';
  uploadForm: FormGroup;
  private readonly notifier: NotifierService;

  selectedDocument = '';
  selectedGroup = '';
  progress = 0;
  selectedFile: File;
  message = '';
  id;

  constructor(public dialogRef: MatDialogRef<FileUploadComponent>, @Inject(MAT_DIALOG_DATA) public data, private formBuilder: FormBuilder,
              private httpClient: HttpClient,
              notifierService: NotifierService, private currentRoute: ActivatedRoute,
              private authService: LoginServiceService) {
    this.notifier = notifierService;
    this.selectedDocument = data.doctype;
    this.selectedGroup = data.groupname;
    this.id = data.docId;
  }

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      file: new FormControl(Array<File>(), Validators.required)
    });
  }


  ngAfterContentInit() {
    this.fileInput.nativeElement.addEventListener('change', () => {
      this.labelFileName.nativeElement.innerHTML = this.fileInput.nativeElement.files[0].name;
      this.selectedFile = this.fileInput.nativeElement.files[0];
      if (this.fileInput.nativeElement.files[0].size >= 1048576) {
        this.uploadForm.controls['file'].setErrors({ 'invalid': true});
        this.message = 'Plik jest za duży!';
      } else {
        this.message = '';
      }
    });
  }


  onSubmit() {

    this.progress = 0;

    this.authService.upload(this.SERVER_URL + '/' + this.id, this.selectedFile).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.notifier.notify('success', 'Pomyślnie wysłano dokument: ' + this.docType.nativeElement.value);
          // this.fileInfos = this.uploadService.getFiles();
        }
      },
      err => {
        this.progress = 0;
        this.notifier.notify('error', 'Wystąpił błąd przy wysyłaniu dokumentu');
        this.selectedDocument = undefined;
      });

    // this.selectedFiles = undefined;

    // const formData = new FormData();
    // formData.append('file', this.uploadForm.value.file);
    //  formData.append('type', this.docType.nativeElement.value);

    // this.httpClient.post<any>(this.SERVER_URL + '/1', formData).subscribe(
    //   (res) => {
    //     console.log(res);
    //     this.notifier.notify('success', 'Pomyślnie wysłano: ' + this.uploadForm.get('documentType').value);
    //   },
    //   (err) => {
    //     console.log(err);
    //     this.notifier.notify('error', 'Wystąpił błąd przy wysyłaniu dokumentu: ' );
    //   }
    // );


  }

}
