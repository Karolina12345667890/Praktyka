import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { FileUploadComponent } from './file-upload/file-upload.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { OswiadczenieComponent } from './oswiadczenie/oswiadczenie.component';
import { NavbarComponent } from './navbar/navbar.component';
import { PorozumienieComponent } from './porozumienie/porozumienie.component';
import { LoginComponent } from './login/login.component';
import {LoginServiceService} from "./login-service.service";
import {AuthGuardService} from "./auth-guard.service";
import { ZaswiadczenieComponent } from './zaswiadczenie/zaswiadczenie.component';
import { AnkietaDlaStudentaComponent } from './ankieta-dla-studenta/ankieta-dla-studenta.component';
import { AnkietaDlaPracodawcyComponent } from './ankieta-dla-pracodawcy/ankieta-dla-pracodawcy.component';
import { DziennikPraktykComponent } from './dziennik-praktyk/dziennik-praktyk.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FileUploadComponent,
    NavbarComponent,
    OswiadczenieComponent,
    PorozumienieComponent,
    LoginComponent,
    ZaswiadczenieComponent,
    AnkietaDlaStudentaComponent,
    AnkietaDlaPracodawcyComponent,
    DziennikPraktykComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [LoginServiceService,AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
