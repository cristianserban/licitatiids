import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOcol } from 'app/shared/model/ocol.model';

type EntityResponseType = HttpResponse<IOcol>;
type EntityArrayResponseType = HttpResponse<IOcol[]>;

@Injectable({ providedIn: 'root' })
export class OcolService {
  public resourceUrl = SERVER_API_URL + 'api/ocols';

  constructor(protected http: HttpClient) {}

  create(ocol: IOcol): Observable<EntityResponseType> {
    return this.http.post<IOcol>(this.resourceUrl, ocol, { observe: 'response' });
  }

  update(ocol: IOcol): Observable<EntityResponseType> {
    return this.http.put<IOcol>(this.resourceUrl, ocol, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOcol>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOcol[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
