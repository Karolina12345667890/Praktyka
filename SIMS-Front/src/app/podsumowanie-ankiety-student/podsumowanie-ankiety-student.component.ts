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
  answers: Array<any> = new Array<any>();
  questionFiveTexts: Array<string> = new Array<string>();
  questionSevenTexts: Array<string> = new Array<string>();
  questionElevenTexts: Array<string> = new Array<string>();
  questionTwelveTexts: Array<string> = new Array<string>();
  questionThirteenTexts: Array<string> = new Array<string>();
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
          this.answers.push(['Pytanie 1 - Praktykę odbywał(a) Pan(i) w miejscu zamieszkania?', value[0] ]);
          this.answers.push(['Pytanie 2 - Atmosferę w miejscu odbywania praktyki ocenia Pan(i) jako?', value[1] ]);
          this.answers.push(['Pytanie 3 - Czy Zakładowy Opiekun Praktyk służył Panu(i) pomocą?', value[2] ]);
          this.answers.push(['Pytanie 4 - Czy program praktyki był zrealizowany?', value[3] ]);
          this.answers.push(['Pytanie 5 - Czy miał(a) Pan(i) problemy z przystosowaniem się do warunków pracy wmiejscu odbywania praktyki?', value[4] ]);
          this.answers.push(['Pytanie 6 - Czy potrafił(a) Pan(i) pracować w zespole?', value[6] ]);
          this.answers.push(['Pytanie 7 - Czy realizacja zadań stawianych przed Panem(ią) na praktyce wymagała od Pana(i) dodatkowego przygotowania się do niej?', value[7] ]);
          this.answers.push(['Pytanie 8 - Czy w trakcie praktyki wykorzystywał(a) Pan(i) wiedzę i umiejętności nabyte podczas zajęć na Uczelni?', value[9] ]);
          this.answers.push(['Pytanie 9 - Czy odbyta praktyka pozwoliła Panu(i) na podniesienie poziomu: wiedzy, umiejętności, kompetencji społecznych?', [value[10], value[11], value[12]] ]);
          this.answers.push(['Pytanie 10 - Czy i w jakim stopniu umiejętności nabyte podczas praktyk mogą okazać się przydatne w dalszych studiach i/lub pracy zawodowej?', value[13] ]);
          this.answers.push(['Pytanie 11 - Czy przebyta praktyka zachęciła Pana(ią) do wykonywania zawodu związanego z wybranym kierunkiem studiów?', value[14] ]);
          this.answers.push(['Pytanie 12 - Czy jest Pan(i) zadowolony z odbytej praktyki?', value[16]]);
          this.answers.push(['Pytanie 13 - Czy praktyki zawodowe są Pana(i) zdaniem potrzebne?', value[19] ]);

          if (value[5] != null && value[5] != '.....') {
            this.questionFiveTexts.push(value[5]);
          }

          if (value[8] != null && value[8] != '....') {
            this.questionSevenTexts.push(value[8]);
          }

          if (value[15] != null && value[15] != '.....') {
            this.questionElevenTexts.push(value[15]);
          }


          if (value[17] != null && value[17] != '....') {
            this.questionTwelveTexts.push(value[17]);
          }
          else if (value[18] != null && value[18] != '....') {
            this.questionTwelveTexts.push(value[18]);
          }

          if (value[20] != null && value[20] != '....') {
            this.questionThirteenTexts.push(value[20]);
          }

          if (value[21] != null && value[21] != '.....') {
            this.suggestions.push(value[21]);
          }
        },
          error => this.err = error.error.message
      );
    }, 50);
  }

}
