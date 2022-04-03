export type Product = {
  sku: number;
  category: number | null;
  name: string;
  description: string;
  price_base: number;
  price_sale: number;
  sale_value: number;
  sale_id: number | null;
  currency: string;
  quantity: number | null;
  unit: string | null;
  warehouse_id: number | null;
  company_id: number;
  photos: Array<string | File>;
};
