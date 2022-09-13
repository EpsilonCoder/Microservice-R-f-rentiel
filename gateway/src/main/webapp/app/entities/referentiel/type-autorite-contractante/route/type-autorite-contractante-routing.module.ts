import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypeAutoriteContractanteComponent } from '../list/type-autorite-contractante.component';
import { TypeAutoriteContractanteDetailComponent } from '../detail/type-autorite-contractante-detail.component';
import { TypeAutoriteContractanteUpdateComponent } from '../update/type-autorite-contractante-update.component';
import { TypeAutoriteContractanteRoutingResolveService } from './type-autorite-contractante-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const typeAutoriteContractanteRoute: Routes = [
  {
    path: '',
    component: TypeAutoriteContractanteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeAutoriteContractanteDetailComponent,
    resolve: {
      typeAutoriteContractante: TypeAutoriteContractanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeAutoriteContractanteUpdateComponent,
    resolve: {
      typeAutoriteContractante: TypeAutoriteContractanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeAutoriteContractanteUpdateComponent,
    resolve: {
      typeAutoriteContractante: TypeAutoriteContractanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typeAutoriteContractanteRoute)],
  exports: [RouterModule],
})
export class TypeAutoriteContractanteRoutingModule {}
