export type Product = {
  sku: number;
  category: string;
  name: string;
  description: string;
  price_base: number;
  price_sale: number;
  currency: string;
  quantity: number;
  unit: string;
  photo_main: string;
  warehouse_id: string;
  company_id?: number;
};
