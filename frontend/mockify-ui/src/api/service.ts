export interface Service {
  create: (hooName: string) => Promise<Event>;
  delete: (hookName: string) => Promise<any>;
  getAll: (hookName: string) => Promise<Event[]>;
}
