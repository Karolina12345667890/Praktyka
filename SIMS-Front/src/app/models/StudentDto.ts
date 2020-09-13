import {DocumentDto} from "./DocumentDto";

export interface StudentDto {
  id: number;
  name: string;
  surname: string;
  email: string;
  album: number;
  documents: DocumentDto [];
  // nie ma w bazie danych tego pola u studenta
  companyName : string;
}
