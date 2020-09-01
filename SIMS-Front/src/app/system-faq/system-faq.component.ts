import {AfterViewInit, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-system-faq',
  templateUrl: './system-faq.component.html',
  styleUrls: ['./system-faq.component.css']
})
export class SystemFaqComponent implements OnInit, AfterViewInit {

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    const elements = document.getElementsByClassName('howto');

    Array.from(elements as HTMLCollectionOf<HTMLElement>).forEach(element => {
      const childHeader = <HTMLElement>element.childNodes[0];
      childHeader.addEventListener('click', () => {
        const childBody = <HTMLElement>element.childNodes[1];
        if (childBody.hasAttribute('style')) {
          childHeader.classList.add('howto-opened');
          childHeader.classList.remove('border-bottom-0');
          childBody.removeAttribute('style');
        }
        else {
          childHeader.classList.remove('howto-opened');
          childHeader.classList.add('border-bottom-0');
          childBody.style.display = 'none';
        }
      })
    })
  }

}
