export interface Carburant {
  id: number;
  nom: string;
  description?: string;
}

export interface CarburantPage {
  content: Carburant[];
  pageable: { pageNumber: number; pageSize: number };
  totalElements: number;
  totalPages: number;
}

export interface CreateCarburantRequest {
  nom: string;
  description?: string;
}

