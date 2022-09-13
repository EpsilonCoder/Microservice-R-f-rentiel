export interface IDepartement {
  id: number;
  libelle?: string | null;
}

export type NewDepartement = Omit<IDepartement, 'id'> & { id: null };
