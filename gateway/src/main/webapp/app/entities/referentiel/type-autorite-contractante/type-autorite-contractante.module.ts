import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypeAutoriteContractanteComponent } from './list/type-autorite-contractante.component';
import { TypeAutoriteContractanteDetailComponent } from './detail/type-autorite-contractante-detail.component';
import { TypeAutoriteContractanteUpdateComponent } from './update/type-autorite-contractante-update.component';
import { TypeAutoriteContractanteDeleteDialogComponent } from './delete/type-autorite-contractante-delete-dialog.component';
import { TypeAutoriteContractanteRoutingModule } from './route/type-autorite-contractante-routing.module';

@NgModule({
  imports: [SharedModule, TypeAutoriteContractanteRoutingModule],
  declarations: [
    TypeAutoriteContractanteComponent,
    TypeAutoriteContractanteDetailComponent,
    TypeAutoriteContractanteUpdateComponent,
    TypeAutoriteContractanteDeleteDialogComponent,
  ],
})
export class ReferentielTypeAutoriteContractanteModule {}
