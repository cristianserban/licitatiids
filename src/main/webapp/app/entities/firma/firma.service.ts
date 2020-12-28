import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFirma } from 'app/shared/model/firma.model';

type EntityResponseType = HttpResponse<IFirma>;
type EntityArrayResponseType = HttpResponse<IFirma[]>;

@Injectable({ providedIn: 'root' })
export class FirmaService {
  public resourceUrl = SERVER_API_URL + 'api/firmas';

  constructor(protected http: HttpClient) {}

  create(firma: IFirma): Observable<EntityResponseType> {
    return this.http.post<IFirma>(this.resourceUrl, firma, { observe: 'response' });
  }

  update(firma: IFirma): Observable<EntityResponseType> {
    return this.http.put<IFirma>(this.resourceUrl, firma, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFirma>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFirma[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
