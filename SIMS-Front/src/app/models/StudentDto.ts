import {DocumentDto} from "./DocumentDto";

export interface StudentDto {
  id: number;
  name: string;
  surname: string;
  email: string;
  album: number;
  documents: DocumentDto [];
}
