import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TypeAutoriteContractanteFormService, TypeAutoriteContractanteFormGroup } from './type-autorite-contractante-form.service';
import { ITypeAutoriteContractante } from '../type-autorite-contractante.model';
import { TypeAutoriteContractanteService } from '../service/type-autorite-contractante.service';

@Component({
  selector: 'jhi-type-autorite-contractante-update',
  templateUrl: './type-autorite-contractante-update.component.html',
})
export class TypeAutoriteContractanteUpdateComponent implements OnInit {
  isSaving = false;
  typeAutoriteContractante: ITypeAutoriteContractante | null = null;

  editForm: TypeAutoriteContractanteFormGroup = this.typeAutoriteContractanteFormService.createTypeAutoriteContractanteFormGroup();

  constructor(
    protected typeAutoriteContractanteService: TypeAutoriteContractanteService,
    protected typeAutoriteContractanteFormService: TypeAutoriteContractanteFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAutoriteContractante }) => {
      this.typeAutoriteContractante = typeAutoriteContractante;
      if (typeAutoriteContractante) {
        this.updateForm(typeAutoriteContractante);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeAutoriteContractante = this.typeAutoriteContractanteFormService.getTypeAutoriteContractante(this.editForm);
    if (typeAutoriteContractante.id !== null) {
      this.subscribeToSaveResponse(this.typeAutoriteContractanteService.update(typeAutoriteContractante));
    } else {
      this.subscribeToSaveResponse(this.typeAutoriteContractanteService.create(typeAutoriteContractante));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeAutoriteContractante>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(typeAutoriteContractante: ITypeAutoriteContractante): void {
    this.typeAutoriteContractante = typeAutoriteContractante;
    this.typeAutoriteContractanteFormService.resetForm(this.editForm, typeAutoriteContractante);
  }
}
