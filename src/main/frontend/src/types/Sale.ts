export type Sale = {
  id?: number;
  name: string;
  type: 'fixed' | 'procent';
  value: number;
  is_active: boolean;
};
