import { ILot } from 'app/shared/model/lot.model';
import { IFirma } from 'app/shared/model/firma.model';

export interface IOferta {
  id?: number;
  castigatoare?: boolean;
  pas?: number;
  pret?: number;
  lot?: ILot;
  firma?: IFirma;
}

export class Oferta implements IOferta {
  constructor(
    public id?: number,
    public castigatoare?: boolean,
    public pas?: number,
    public pret?: number,
    public lot?: ILot,
    public firma?: IFirma
  ) {
    this.castigatoare = this.castigatoare || false;
  }
}
