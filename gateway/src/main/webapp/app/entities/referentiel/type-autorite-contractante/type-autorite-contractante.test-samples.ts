import { ITypeAutoriteContractante, NewTypeAutoriteContractante } from './type-autorite-contractante.model';

export const sampleWithRequiredData: ITypeAutoriteContractante = {
  id: 72005,
};

export const sampleWithPartialData: ITypeAutoriteContractante = {
  id: 34834,
  code: 'Bourgogne',
};

export const sampleWithFullData: ITypeAutoriteContractante = {
  id: 44787,
  code: 'Bourgogne parse web-enabled',
  description: 'Small Designer',
  libelle: 'Computer',
  ordre: 66434,
};

export const sampleWithNewData: NewTypeAutoriteContractante = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
