import { Moment } from 'moment';
import { IFirma } from 'app/shared/model/firma.model';
import { ILot } from 'app/shared/model/lot.model';
import { TipLicitatie } from 'app/shared/model/enumerations/tip-licitatie.model';

export interface ILicitatie {
  id?: number;
  dataLicitatie?: Moment;
  activa?: boolean;
  tipLicitatie?: TipLicitatie;
  firmas?: IFirma[];
  lots?: ILot[];
}

export class Licitatie implements ILicitatie {
  constructor(
    public id?: number,
    public dataLicitatie?: Moment,
    public activa?: boolean,
    public tipLicitatie?: TipLicitatie,
    public firmas?: IFirma[],
    public lots?: ILot[]
  ) {
    this.activa = this.activa || false;
  }
}
