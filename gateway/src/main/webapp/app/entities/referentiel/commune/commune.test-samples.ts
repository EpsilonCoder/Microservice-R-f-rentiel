import { Type } from 'app/entities/enumerations/type.model';

import { ICommune, NewCommune } from './commune.model';

export const sampleWithRequiredData: ICommune = {
  id: 22585,
};

export const sampleWithPartialData: ICommune = {
  id: 72981,
  addresse: 'Consultant',
  faxe: 'Cambridgeshire SMTP',
  sigle: 'emulation',
};

export const sampleWithFullData: ICommune = {
  id: 57480,
  addresse: 'pixel Tilsitt b',
  denomination: 'Salad Franc Clothing',
  faxe: 'transform',
  logo: 'Steel Sainte-Lucie quantifying',
  sigle: 'de a withdrawal',
  telephone: '+33 634063001',
  urlsiteweb: 'Account revolutionary',
  type: Type['EXEMPLE'],
};

export const sampleWithNewData: NewCommune = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
