import { IFirma } from 'app/shared/model/firma.model';

export interface IGarantie {
  id?: number;
  garantie?: number;
  tarif?: number;
  garantieDepusa?: number;
  firma?: IFirma;
}

export class Garantie implements IGarantie {
  constructor(public id?: number, public garantie?: number, public tarif?: number, public garantieDepusa?: number, public firma?: IFirma) {}
}
