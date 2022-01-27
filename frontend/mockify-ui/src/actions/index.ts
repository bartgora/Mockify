import { Dispatch } from "react";
import events from "../api/events";
import { ActionType } from "./types";

export interface Request {
  method: string;
  body: String;
}

export interface Respone {
  body: String;
}

export interface Event {
  request: Request;
  response: Respone;
}

export interface FecthAction {
  payload: Event[];
  type: ActionType;
}

export const fetchData = (path: string) => {
  return async (dispatch: Dispatch<FecthAction>) => {
    const response = await events.get(`${path}`);

    dispatch({
      type: ActionType.FETCH_DATA,
      payload: response.data,
    });
  };
};
