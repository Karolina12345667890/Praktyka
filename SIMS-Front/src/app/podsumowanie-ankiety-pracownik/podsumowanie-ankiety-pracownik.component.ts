import { Component, OnInit } from '@angular/core';
import {LoginServiceService} from "../login-service.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-podsumowanie-ankiety-pracownik',
  templateUrl: './podsumowanie-ankiety-pracownik.component.html',
  styleUrls: ['./podsumowanie-ankiety-pracownik.component.css']
})
export class PodsumowanieAnkietyPracownikComponent implements OnInit {

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
        `http://localhost:8080/api/document/ankieta_pracownik/${this.id}/summaryPracownikSurvay`
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
