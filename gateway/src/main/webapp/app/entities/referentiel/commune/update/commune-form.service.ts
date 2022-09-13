import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICommune, NewCommune } from '../commune.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommune for edit and NewCommuneFormGroupInput for create.
 */
type CommuneFormGroupInput = ICommune | PartialWithRequiredKeyOf<NewCommune>;

type CommuneFormDefaults = Pick<NewCommune, 'id'>;

type CommuneFormGroupContent = {
  id: FormControl<ICommune['id'] | NewCommune['id']>;
  addresse: FormControl<ICommune['addresse']>;
  denomination: FormControl<ICommune['denomination']>;
  faxe: FormControl<ICommune['faxe']>;
  logo: FormControl<ICommune['logo']>;
  sigle: FormControl<ICommune['sigle']>;
  telephone: FormControl<ICommune['telephone']>;
  urlsiteweb: FormControl<ICommune['urlsiteweb']>;
  type: FormControl<ICommune['type']>;
  typeautorite: FormControl<ICommune['typeautorite']>;
  department: FormControl<ICommune['department']>;
};

export type CommuneFormGroup = FormGroup<CommuneFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommuneFormService {
  createCommuneFormGroup(commune: CommuneFormGroupInput = { id: null }): CommuneFormGroup {
    const communeRawValue = {
      ...this.getFormDefaults(),
      ...commune,
    };
    return new FormGroup<CommuneFormGroupContent>({
      id: new FormControl(
        { value: communeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      addresse: new FormControl(communeRawValue.addresse),
      denomination: new FormControl(communeRawValue.denomination),
      faxe: new FormControl(communeRawValue.faxe),
      logo: new FormControl(communeRawValue.logo),
      sigle: new FormControl(communeRawValue.sigle),
      telephone: new FormControl(communeRawValue.telephone),
      urlsiteweb: new FormControl(communeRawValue.urlsiteweb),
      type: new FormControl(communeRawValue.type),
      typeautorite: new FormControl(communeRawValue.typeautorite),
      department: new FormControl(communeRawValue.department),
    });
  }

  getCommune(form: CommuneFormGroup): ICommune | NewCommune {
    return form.getRawValue() as ICommune | NewCommune;
  }

  resetForm(form: CommuneFormGroup, commune: CommuneFormGroupInput): void {
    const communeRawValue = { ...this.getFormDefaults(), ...commune };
    form.reset(
      {
        ...communeRawValue,
        id: { value: communeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CommuneFormDefaults {
    return {
      id: null,
    };
  }
}
