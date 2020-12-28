import { IGarantie } from 'app/shared/model/garantie.model';
import { IOferta } from 'app/shared/model/oferta.model';
import { ILicitatie } from 'app/shared/model/licitatie.model';

export interface IFirma {
  id?: number;
  numeFirma?: string;
  dreptPreemtiune?: boolean;
  volum?: number;
  garanties?: IGarantie[];
  ofertas?: IOferta[];
  licitaties?: ILicitatie[];
}

export class Firma implements IFirma {
  constructor(
    public id?: number,
    public numeFirma?: string,
    public dreptPreemtiune?: boolean,
    public volum?: number,
    public garanties?: IGarantie[],
    public ofertas?: IOferta[],
    public licitaties?: ILicitatie[]
  ) {
    this.dreptPreemtiune = this.dreptPreemtiune || false;
  }
}
