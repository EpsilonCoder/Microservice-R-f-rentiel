export interface ITypeAutoriteContractante {
  id: number;
  code?: string | null;
  description?: string | null;
  libelle?: string | null;
  ordre?: number | null;
}

export type NewTypeAutoriteContractante = Omit<ITypeAutoriteContractante, 'id'> & { id: null };
