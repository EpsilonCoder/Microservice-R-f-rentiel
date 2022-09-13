import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CommuneFormService, CommuneFormGroup } from './commune-form.service';
import { ICommune } from '../commune.model';
import { CommuneService } from '../service/commune.service';
import { ITypeAutoriteContractante } from 'app/entities/referentiel/type-autorite-contractante/type-autorite-contractante.model';
import { TypeAutoriteContractanteService } from 'app/entities/referentiel/type-autorite-contractante/service/type-autorite-contractante.service';
import { IDepartement } from 'app/entities/referentiel/departement/departement.model';
import { DepartementService } from 'app/entities/referentiel/departement/service/departement.service';
import { Type } from 'app/entities/enumerations/type.model';

@Component({
  selector: 'jhi-commune-update',
  templateUrl: './commune-update.component.html',
})
export class CommuneUpdateComponent implements OnInit {
  isSaving = false;
  commune: ICommune | null = null;
  typeValues = Object.keys(Type);

  typeAutoriteContractantesSharedCollection: ITypeAutoriteContractante[] = [];
  departementsSharedCollection: IDepartement[] = [];

  editForm: CommuneFormGroup = this.communeFormService.createCommuneFormGroup();

  constructor(
    protected communeService: CommuneService,
    protected communeFormService: CommuneFormService,
    protected typeAutoriteContractanteService: TypeAutoriteContractanteService,
    protected departementService: DepartementService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTypeAutoriteContractante = (o1: ITypeAutoriteContractante | null, o2: ITypeAutoriteContractante | null): boolean =>
    this.typeAutoriteContractanteService.compareTypeAutoriteContractante(o1, o2);

  compareDepartement = (o1: IDepartement | null, o2: IDepartement | null): boolean => this.departementService.compareDepartement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commune }) => {
      this.commune = commune;
      if (commune) {
        this.updateForm(commune);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commune = this.communeFormService.getCommune(this.editForm);
    if (commune.id !== null) {
      this.subscribeToSaveResponse(this.communeService.update(commune));
    } else {
      this.subscribeToSaveResponse(this.communeService.create(commune));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommune>>): void {
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

  protected updateForm(commune: ICommune): void {
    this.commune = commune;
    this.communeFormService.resetForm(this.editForm, commune);

    this.typeAutoriteContractantesSharedCollection =
      this.typeAutoriteContractanteService.addTypeAutoriteContractanteToCollectionIfMissing<ITypeAutoriteContractante>(
        this.typeAutoriteContractantesSharedCollection,
        commune.typeautorite
      );
    this.departementsSharedCollection = this.departementService.addDepartementToCollectionIfMissing<IDepartement>(
      this.departementsSharedCollection,
      commune.department
    );
  }

  protected loadRelationshipsOptions(): void {
    this.typeAutoriteContractanteService
      .query()
      .pipe(map((res: HttpResponse<ITypeAutoriteContractante[]>) => res.body ?? []))
      .pipe(
        map((typeAutoriteContractantes: ITypeAutoriteContractante[]) =>
          this.typeAutoriteContractanteService.addTypeAutoriteContractanteToCollectionIfMissing<ITypeAutoriteContractante>(
            typeAutoriteContractantes,
            this.commune?.typeautorite
          )
        )
      )
      .subscribe(
        (typeAutoriteContractantes: ITypeAutoriteContractante[]) =>
          (this.typeAutoriteContractantesSharedCollection = typeAutoriteContractantes)
      );

    this.departementService
      .query()
      .pipe(map((res: HttpResponse<IDepartement[]>) => res.body ?? []))
      .pipe(
        map((departements: IDepartement[]) =>
          this.departementService.addDepartementToCollectionIfMissing<IDepartement>(departements, this.commune?.department)
        )
      )
      .subscribe((departements: IDepartement[]) => (this.departementsSharedCollection = departements));
  }
}
