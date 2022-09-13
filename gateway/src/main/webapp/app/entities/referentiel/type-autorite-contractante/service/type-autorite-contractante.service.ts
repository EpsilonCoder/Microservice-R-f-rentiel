import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypeAutoriteContractante, NewTypeAutoriteContractante } from '../type-autorite-contractante.model';

export type PartialUpdateTypeAutoriteContractante = Partial<ITypeAutoriteContractante> & Pick<ITypeAutoriteContractante, 'id'>;

export type EntityResponseType = HttpResponse<ITypeAutoriteContractante>;
export type EntityArrayResponseType = HttpResponse<ITypeAutoriteContractante[]>;

@Injectable({ providedIn: 'root' })
export class TypeAutoriteContractanteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/type-autorite-contractantes', 'referentiel');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typeAutoriteContractante: NewTypeAutoriteContractante): Observable<EntityResponseType> {
    return this.http.post<ITypeAutoriteContractante>(this.resourceUrl, typeAutoriteContractante, { observe: 'response' });
  }

  update(typeAutoriteContractante: ITypeAutoriteContractante): Observable<EntityResponseType> {
    return this.http.put<ITypeAutoriteContractante>(
      `${this.resourceUrl}/${this.getTypeAutoriteContractanteIdentifier(typeAutoriteContractante)}`,
      typeAutoriteContractante,
      { observe: 'response' }
    );
  }

  partialUpdate(typeAutoriteContractante: PartialUpdateTypeAutoriteContractante): Observable<EntityResponseType> {
    return this.http.patch<ITypeAutoriteContractante>(
      `${this.resourceUrl}/${this.getTypeAutoriteContractanteIdentifier(typeAutoriteContractante)}`,
      typeAutoriteContractante,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeAutoriteContractante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeAutoriteContractante[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTypeAutoriteContractanteIdentifier(typeAutoriteContractante: Pick<ITypeAutoriteContractante, 'id'>): number {
    return typeAutoriteContractante.id;
  }

  compareTypeAutoriteContractante(
    o1: Pick<ITypeAutoriteContractante, 'id'> | null,
    o2: Pick<ITypeAutoriteContractante, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getTypeAutoriteContractanteIdentifier(o1) === this.getTypeAutoriteContractanteIdentifier(o2) : o1 === o2;
  }

  addTypeAutoriteContractanteToCollectionIfMissing<Type extends Pick<ITypeAutoriteContractante, 'id'>>(
    typeAutoriteContractanteCollection: Type[],
    ...typeAutoriteContractantesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const typeAutoriteContractantes: Type[] = typeAutoriteContractantesToCheck.filter(isPresent);
    if (typeAutoriteContractantes.length > 0) {
      const typeAutoriteContractanteCollectionIdentifiers = typeAutoriteContractanteCollection.map(
        typeAutoriteContractanteItem => this.getTypeAutoriteContractanteIdentifier(typeAutoriteContractanteItem)!
      );
      const typeAutoriteContractantesToAdd = typeAutoriteContractantes.filter(typeAutoriteContractanteItem => {
        const typeAutoriteContractanteIdentifier = this.getTypeAutoriteContractanteIdentifier(typeAutoriteContractanteItem);
        if (typeAutoriteContractanteCollectionIdentifiers.includes(typeAutoriteContractanteIdentifier)) {
          return false;
        }
        typeAutoriteContractanteCollectionIdentifiers.push(typeAutoriteContractanteIdentifier);
        return true;
      });
      return [...typeAutoriteContractantesToAdd, ...typeAutoriteContractanteCollection];
    }
    return typeAutoriteContractanteCollection;
  }
}
