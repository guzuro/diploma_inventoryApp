export type Sale = {
  id?: number;
  name: string;
  type: 'fixed' | 'percent';
  value: number;
  is_active: boolean;
};
