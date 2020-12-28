import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILicitatie } from 'app/shared/model/licitatie.model';

type EntityResponseType = HttpResponse<ILicitatie>;
type EntityArrayResponseType = HttpResponse<ILicitatie[]>;

@Injectable({ providedIn: 'root' })
export class LicitatieService {
  public resourceUrl = SERVER_API_URL + 'api/licitaties';

  constructor(protected http: HttpClient) {}

  create(licitatie: ILicitatie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(licitatie);
    return this.http
      .post<ILicitatie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(licitatie: ILicitatie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(licitatie);
    return this.http
      .put<ILicitatie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILicitatie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILicitatie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(licitatie: ILicitatie): ILicitatie {
    const copy: ILicitatie = Object.assign({}, licitatie, {
      dataLicitatie: licitatie.dataLicitatie && licitatie.dataLicitatie.isValid() ? licitatie.dataLicitatie.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataLicitatie = res.body.dataLicitatie ? moment(res.body.dataLicitatie) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((licitatie: ILicitatie) => {
        licitatie.dataLicitatie = licitatie.dataLicitatie ? moment(licitatie.dataLicitatie) : undefined;
      });
    }
    return res;
  }
}
