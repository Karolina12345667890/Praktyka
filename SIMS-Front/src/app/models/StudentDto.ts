import {DocumentDto} from "./DocumentDto";

export interface StudentDto {
  id: number;
  name: string;
  surname: string;
  email: string;
  albumNumber:number;
  documents: DocumentDto [];
}
