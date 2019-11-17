
export class OswiadczenieDto {
  private id: number = null;
  private opiekunI: string;
  private opiekunN: string;
  private opiekunMail: string;
  private opiekunTel: string;


  constructor(id: number = null, opiekunI: string, opiekunN: string, opiekunMail: string, opiekunTel: string) {
    this.id = id;
    this.opiekunI = opiekunI;
    this.opiekunN = opiekunN;
    this.opiekunMail = opiekunMail;
    this.opiekunTel = opiekunTel;
  }

}
