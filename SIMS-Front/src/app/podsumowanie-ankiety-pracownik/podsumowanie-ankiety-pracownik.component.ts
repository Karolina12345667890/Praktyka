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
  answers: Array<any> = new Array<any>();
  questionSixteenTexts: Array<string> = new Array<string>();
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
          this.answers.push(['Pytanie 1 - Kierunek studiów studentów odbywających praktyki w Państwa zakładzie pracy', value[0]]);
          this.answers.push(['Pytanie 2 - Wiedza niezbędna do szybkiego opanowania umiejętności wykonywania zadań realizowanych w Państwa instytucji', value[1]]);
          this.answers.push(['Pytanie 3 - Ogólne przygotowanie zawodowe', value[2]]);
          this.answers.push(['Pytanie 4 - Umiejętność nawiązywania kontaktów', value[3]]);
          this.answers.push(['Pytanie 5 - Umiejętność organizacji pracy', value[4]]);
          this.answers.push(['Pytanie 6 - Umiejętność pracy w zespole', value[5]]);
          this.answers.push(['Pytanie 7 - Umiejętność rozwiązywania problemów (związanych z wykonywaną pracą)', value[6]]);
          this.answers.push(['Pytanie 8 - Umiejętność argumentacji i przekonywania', value[7]]);
          this.answers.push(['Pytanie 9 - Umiejętność sprawnego uczenia się nowych zagadnień niezbędnych w pracy', value[8]]);
          this.answers.push(['Pytanie 10 - Czy studenci są kreatywni?', value[9]]);
          this.answers.push(['Pytanie 11 - Czy studenci są samodzielni?', value[10]]);
          this.answers.push(['Pytanie 12 - Czy studenci są sumienni?', value[11]]);
          this.answers.push(['Pytanie 13 - Czy studenci są obowiązkowi?', value[12]]);
          this.answers.push(['Pytanie 14 - Czy pracodawcy powinni być bliżej związani z Uniwersytetem?', value[13]]);
          this.answers.push(['Pytanie 15 - Czy podczas studiów studenci powinni pracować?', value[14]]);
          this.answers.push(['Pytanie 16 - Czy sądzicie Państwo, że plany i programy nauczania są dopasowane do potrzeb pracodawców, uwzględniając ich różnorodność, a jeśli nie to jakie zmiany by Pan/Pani proponował/a?', value[15]]);

          if (value[16] != '' && value[16] != null) {
            this.questionSixteenTexts.push(value[16]);
          }

          if (value[17] != '' && value[17] != null) {
            this.suggestions.push(value[17]);
          }
        },
        error => this.err = error.error.message
      );
    }, 50);
  }

}
