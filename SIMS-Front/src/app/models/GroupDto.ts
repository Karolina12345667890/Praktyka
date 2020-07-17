
import {StudentDto} from "./StudentDto";

export interface GroupDto {
  id: number;
  groupName: string;
  durationInWeeks: number;
  startDate: string;
  isOpen:boolean;
  fieldOfStudy : string;
  formOfStudy :string;
  speciality : string;
  changed : boolean;
  groupAdminId : number;
  groupAdminName : string;
  groupAdminSurname : string;

  students : StudentDto [];
}
