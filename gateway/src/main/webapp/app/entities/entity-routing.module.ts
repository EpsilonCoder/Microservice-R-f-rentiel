import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'commune',
        data: { pageTitle: 'gatewayApp.referentielCommune.home.title' },
        loadChildren: () => import('./referentiel/commune/commune.module').then(m => m.ReferentielCommuneModule),
      },
      {
        path: 'type-autorite-contractante',
        data: { pageTitle: 'gatewayApp.referentielTypeAutoriteContractante.home.title' },
        loadChildren: () =>
          import('./referentiel/type-autorite-contractante/type-autorite-contractante.module').then(
            m => m.ReferentielTypeAutoriteContractanteModule
          ),
      },
      {
        path: 'departement',
        data: { pageTitle: 'gatewayApp.referentielDepartement.home.title' },
        loadChildren: () => import('./referentiel/departement/departement.module').then(m => m.ReferentielDepartementModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
