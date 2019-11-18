
import {StudentDto} from "./StudentDto";

export interface GroupDto {
  id: number;
  groupName: string;
  durationInWeeks: number;
  startDate: string;
  isOpen:boolean;
  students : StudentDto [];
}
