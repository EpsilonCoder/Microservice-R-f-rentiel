import { ITypeAutoriteContractante } from 'app/entities/referentiel/type-autorite-contractante/type-autorite-contractante.model';
import { IDepartement } from 'app/entities/referentiel/departement/departement.model';
import { Type } from 'app/entities/enumerations/type.model';

export interface ICommune {
  id: number;
  addresse?: string | null;
  denomination?: string | null;
  faxe?: string | null;
  logo?: string | null;
  sigle?: string | null;
  telephone?: string | null;
  urlsiteweb?: string | null;
  type?: Type | null;
  typeautorite?: Pick<ITypeAutoriteContractante, 'id'> | null;
  department?: Pick<IDepartement, 'id'> | null;
}

export type NewCommune = Omit<ICommune, 'id'> & { id: null };
