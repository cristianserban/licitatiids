import { IOferta } from 'app/shared/model/oferta.model';
import { IOcol } from 'app/shared/model/ocol.model';
import { ILicitatie } from 'app/shared/model/licitatie.model';
import { StareLot } from 'app/shared/model/enumerations/stare-lot.model';

export interface ILot {
  id?: number;
  nrFisa?: number;
  stare?: StareLot;
  pretPornire?: number;
  volumBrut?: number;
  volumNet?: number;
  volumCoaja?: number;
  ofertas?: IOferta[];
  ocol?: IOcol;
  licitaties?: ILicitatie[];
}

export class Lot implements ILot {
  constructor(
    public id?: number,
    public nrFisa?: number,
    public stare?: StareLot,
    public pretPornire?: number,
    public volumBrut?: number,
    public volumNet?: number,
    public volumCoaja?: number,
    public ofertas?: IOferta[],
    public ocol?: IOcol,
    public licitaties?: ILicitatie[]
  ) {}
}
