import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITypeAutoriteContractante, NewTypeAutoriteContractante } from '../type-autorite-contractante.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITypeAutoriteContractante for edit and NewTypeAutoriteContractanteFormGroupInput for create.
 */
type TypeAutoriteContractanteFormGroupInput = ITypeAutoriteContractante | PartialWithRequiredKeyOf<NewTypeAutoriteContractante>;

type TypeAutoriteContractanteFormDefaults = Pick<NewTypeAutoriteContractante, 'id'>;

type TypeAutoriteContractanteFormGroupContent = {
  id: FormControl<ITypeAutoriteContractante['id'] | NewTypeAutoriteContractante['id']>;
  code: FormControl<ITypeAutoriteContractante['code']>;
  description: FormControl<ITypeAutoriteContractante['description']>;
  libelle: FormControl<ITypeAutoriteContractante['libelle']>;
  ordre: FormControl<ITypeAutoriteContractante['ordre']>;
};

export type TypeAutoriteContractanteFormGroup = FormGroup<TypeAutoriteContractanteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TypeAutoriteContractanteFormService {
  createTypeAutoriteContractanteFormGroup(
    typeAutoriteContractante: TypeAutoriteContractanteFormGroupInput = { id: null }
  ): TypeAutoriteContractanteFormGroup {
    const typeAutoriteContractanteRawValue = {
      ...this.getFormDefaults(),
      ...typeAutoriteContractante,
    };
    return new FormGroup<TypeAutoriteContractanteFormGroupContent>({
      id: new FormControl(
        { value: typeAutoriteContractanteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(typeAutoriteContractanteRawValue.code),
      description: new FormControl(typeAutoriteContractanteRawValue.description),
      libelle: new FormControl(typeAutoriteContractanteRawValue.libelle),
      ordre: new FormControl(typeAutoriteContractanteRawValue.ordre),
    });
  }

  getTypeAutoriteContractante(form: TypeAutoriteContractanteFormGroup): ITypeAutoriteContractante | NewTypeAutoriteContractante {
    return form.getRawValue() as ITypeAutoriteContractante | NewTypeAutoriteContractante;
  }

  resetForm(form: TypeAutoriteContractanteFormGroup, typeAutoriteContractante: TypeAutoriteContractanteFormGroupInput): void {
    const typeAutoriteContractanteRawValue = { ...this.getFormDefaults(), ...typeAutoriteContractante };
    form.reset(
      {
        ...typeAutoriteContractanteRawValue,
        id: { value: typeAutoriteContractanteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TypeAutoriteContractanteFormDefaults {
    return {
      id: null,
    };
  }
}
