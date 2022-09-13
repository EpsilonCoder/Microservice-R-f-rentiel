import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeAutoriteContractante } from '../type-autorite-contractante.model';

@Component({
  selector: 'jhi-type-autorite-contractante-detail',
  templateUrl: './type-autorite-contractante-detail.component.html',
})
export class TypeAutoriteContractanteDetailComponent implements OnInit {
  typeAutoriteContractante: ITypeAutoriteContractante | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAutoriteContractante }) => {
      this.typeAutoriteContractante = typeAutoriteContractante;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
