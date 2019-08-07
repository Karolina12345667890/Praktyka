import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { FileUploadComponent } from './file-upload/file-upload.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { OswiadczenieComponent } from './oswiadczenie/oswiadczenie.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FileUploadComponent,
    OswiadczenieComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
