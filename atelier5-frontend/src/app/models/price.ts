export interface PriceEntry {
  id: number;
  date: string; // YYYY-MM-DD
  prix: number;
  stationId: number;
  carburantId: number;
}

export interface CreatePriceRequest {
  carburantId: number;
  date: string; // YYYY-MM-DD
  prix: number;
}

