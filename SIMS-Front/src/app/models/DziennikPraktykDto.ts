import {diaryDto} from "./DiaryDto";

export interface dziennikPraktykDto {

  periodFrom: string;
  periodTo: string;
  studentAlbumNumber: string;
  companyName: string;
  diary: diaryDto[];

}
