export type Product = {
  code?: number,
  category:string,
  name: string,
  description: string,
  price: number,
  price_old?: number,
  currency: string,
  quantity: number,
  unit: string,
  images: any | [],
  imagesRemoved?: FileList | [],
}
