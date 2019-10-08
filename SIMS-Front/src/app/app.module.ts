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
import { StudentListComponent } from './student-list/student-list.component';
import { NotifierModule, NotifierOptions } from 'angular-notifier';

const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: 'right',
      distance: 12
    },
    vertical: {
      position: 'bottom',
      distance: 12,
      gap: 10
    }
  },
  theme: 'material',
  behaviour: {
    autoHide: 5000,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease'
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50
    },
    shift: {
      speed: 300,
      easing: 'ease'
    },
    overlap: 150
  }
};

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
    DziennikPraktykComponent,
    StudentListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NotifierModule.withConfig(customNotifierOptions),
  ],
  providers: [LoginServiceService,AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
