export interface Request {
    method: string;
    body: any;
  }
  
  export interface Respone {
    body: any;
  }
  
  export interface Event {
    request: Request;
    response: Respone;
    timestamp: Date;
  }