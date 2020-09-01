import {answerDto} from "./AnswerDto";

export interface ankietaPracownikDto {
  answerTo15text: string;
  answerTo16text: string;
  answer: answerDto[];
  companyInfo: string;
  numberOfStudent: number;

}
