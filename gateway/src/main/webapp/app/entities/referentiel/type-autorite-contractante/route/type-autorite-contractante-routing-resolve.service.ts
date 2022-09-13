import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypeAutoriteContractante } from '../type-autorite-contractante.model';
import { TypeAutoriteContractanteService } from '../service/type-autorite-contractante.service';

@Injectable({ providedIn: 'root' })
export class TypeAutoriteContractanteRoutingResolveService implements Resolve<ITypeAutoriteContractante | null> {
  constructor(protected service: TypeAutoriteContractanteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeAutoriteContractante | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typeAutoriteContractante: HttpResponse<ITypeAutoriteContractante>) => {
          if (typeAutoriteContractante.body) {
            return of(typeAutoriteContractante.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
