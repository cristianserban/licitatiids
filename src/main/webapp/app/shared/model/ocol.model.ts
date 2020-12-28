import { ILot } from 'app/shared/model/lot.model';

export interface IOcol {
  id?: number;
  numeOcol?: string;
  ordine?: number;
  lots?: ILot[];
}

export class Ocol implements IOcol {
  constructor(public id?: number, public numeOcol?: string, public ordine?: number, public lots?: ILot[]) {}
}
