export interface Station {
  id: number;
  nom: string;
  ville: string;
  adresse: string;
}

export interface StationPage {
  content: Station[];
  pageable: { pageNumber: number; pageSize: number };
  totalElements: number;
  totalPages: number;
}

export interface CreateStationRequest {
  nom: string;
  ville: string;
  adresse: string;
}

