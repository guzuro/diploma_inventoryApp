export type Product = {
  sku:number
  category: number
  name: string
  description: string
  price_base: number
  price_sale?: number
  sale_value?: number | null
  sale_id: number
  currency: string
  quantity: number
  unit: string
  warehouse_id: number
  company_id: number
  photos?: Array<string|File>
};
