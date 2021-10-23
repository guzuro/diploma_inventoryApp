export type Product = {
  code?: string,
  category:string,
  name: string,
  description: string,
  price: number,
  price_old?: number,
  quantity: number,
  unit: string,
  images: FileList | [],
  imagesRemoved?: FileList | [],
}
