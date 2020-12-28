import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGarantie } from 'app/shared/model/garantie.model';

type EntityResponseType = HttpResponse<IGarantie>;
type EntityArrayResponseType = HttpResponse<IGarantie[]>;

@Injectable({ providedIn: 'root' })
export class GarantieService {
  public resourceUrl = SERVER_API_URL + 'api/garanties';

  constructor(protected http: HttpClient) {}

  create(garantie: IGarantie): Observable<EntityResponseType> {
    return this.http.post<IGarantie>(this.resourceUrl, garantie, { observe: 'response' });
  }

  update(garantie: IGarantie): Observable<EntityResponseType> {
    return this.http.put<IGarantie>(this.resourceUrl, garantie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGarantie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGarantie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
