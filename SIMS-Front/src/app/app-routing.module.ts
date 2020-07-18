import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {FileUploadComponent} from './file-upload/file-upload.component';
import {OswiadczenieComponent} from './oswiadczenie/oswiadczenie.component';
import {PorozumienieComponent} from "./porozumienie/porozumienie.component";
import {LoginComponent} from "./login/login.component";
import {AuthGuardService} from "./auth-guard.service";
import {ZaswiadczenieComponent} from "./zaswiadczenie/zaswiadczenie.component";
import {AnkietaDlaStudentaComponent} from "./ankieta-dla-studenta/ankieta-dla-studenta.component";
import {AnkietaDlaPracodawcyComponent} from "./ankieta-dla-pracodawcy/ankieta-dla-pracodawcy.component";
import {DziennikPraktykComponent} from "./dziennik-praktyk/dziennik-praktyk.component";
import {StudentListComponent} from "./student-list/student-list.component";
import {GroupListComponent} from "./group-list/group-list.component";


//tabele rutingu
const appRoutes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'oswiadczenie',
    component: OswiadczenieComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'upload',
    component: FileUploadComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'porozumienie',
    component: PorozumienieComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'zaswiadczenie',
    component: ZaswiadczenieComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'dziennik',
    component: DziennikPraktykComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'ankietastudent',
    component: AnkietaDlaStudentaComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'ankietaprac',
    component: AnkietaDlaPracodawcyComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'sl',
    component: StudentListComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'gl',
    component: GroupListComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full',
  }

];


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(appRoutes,
      // {enableTracing: true} // TODO: remove after (it's for easier debugging only)
    ),
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
