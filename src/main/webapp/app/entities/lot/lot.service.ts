import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILot } from 'app/shared/model/lot.model';

type EntityResponseType = HttpResponse<ILot>;
type EntityArrayResponseType = HttpResponse<ILot[]>;

@Injectable({ providedIn: 'root' })
export class LotService {
  public resourceUrl = SERVER_API_URL + 'api/lots';

  constructor(protected http: HttpClient) {}

  create(lot: ILot): Observable<EntityResponseType> {
    return this.http.post<ILot>(this.resourceUrl, lot, { observe: 'response' });
  }

  update(lot: ILot): Observable<EntityResponseType> {
    return this.http.put<ILot>(this.resourceUrl, lot, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILot>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILot[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
