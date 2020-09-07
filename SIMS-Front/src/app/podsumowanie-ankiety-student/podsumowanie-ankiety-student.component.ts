import { Component, OnInit } from '@angular/core';
import {LoginServiceService} from "../login-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import set = Reflect.set;

@Component({
  selector: 'app-podsumowanie-ankiety-student',
  templateUrl: './podsumowanie-ankiety-student.component.html',
  styleUrls: ['./podsumowanie-ankiety-student.component.css']
})
export class PodsumowanieAnkietyStudentComponent implements OnInit {

  id: number;
  err = null;
  answers: Array<Array<any>> = null;
  suggestions: Array<string> = new Array<string>();

  constructor(private authService: LoginServiceService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    if (!this.authService.isAdmin()) {
      this.router.navigate(['/home']);
    }
    else {
     this.load();
    }
  }

  private load() {
    this.activatedRoute.queryParams.subscribe(param => {
      if (param.id == null) {
        this.id = 1;
      }
      else {
        this.id = param.id;
      }
    });

    setTimeout(() => {
      this.authService.getResource(
        `http://localhost:8080/api/document/ankieta_studenta/${this.id}/summaryStudentSurvay`
      ).subscribe(
        value => {
          this.answers = Object.keys(value).map((key) => value[key]).slice(0,-1);
          this.suggestions = Object.keys(value).map((key) => value[key])[Object.keys(value).map((key) => value[key]).length-1];
        },
          error => this.err = error.error.message
      );
    }, 50);
  }

}
