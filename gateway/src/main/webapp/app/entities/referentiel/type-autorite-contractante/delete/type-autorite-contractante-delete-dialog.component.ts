import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeAutoriteContractante } from '../type-autorite-contractante.model';
import { TypeAutoriteContractanteService } from '../service/type-autorite-contractante.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './type-autorite-contractante-delete-dialog.component.html',
})
export class TypeAutoriteContractanteDeleteDialogComponent {
  typeAutoriteContractante?: ITypeAutoriteContractante;

  constructor(protected typeAutoriteContractanteService: TypeAutoriteContractanteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeAutoriteContractanteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
