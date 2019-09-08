import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {FileUploadComponent} from './file-upload/file-upload.component';
import {OswiadczenieComponent} from './oswiadczenie/oswiadczenie.component';


const appRoutes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'osw',
    component: OswiadczenieComponent
  },
  {
    path: 'upload',
    component: FileUploadComponent
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
