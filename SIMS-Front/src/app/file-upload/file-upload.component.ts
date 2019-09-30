import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  SERVER_URL = 'http://localhost:8080/upload';
  uploadForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private httpClient: HttpClient) {
  }

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      file: new FormControl(Array<File>(), [Validators.required,]),
      documentType: new FormControl('', [Validators.required,]),
    });
  }

  // onFileSelect(event) {
  //    if (event.target.files.length > 0) {
  //      const file = event.target.files[0];
  //      this.uploadForm.get('file').setValue(file);
  //    }
  // }


  onSubmit() {
    console.log(this.uploadForm)
    const formData = new FormData();
    formData.append('file', this.uploadForm.get('file').value);

    this.httpClient.post<any>(this.SERVER_URL, formData).subscribe(
      (res) => console.log(res),
      (err) => console.log(err)
    );
  }

}
