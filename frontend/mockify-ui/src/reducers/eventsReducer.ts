import {FecthAction} from '../actions';
import {ActionType} from '../actions/types';

export const eventsReducer = (state: Request[] = [], action: FecthAction) => {
  switch (action.type) {
    case ActionType.FETCH_DATA:
      return action.payload;
    default:
      return state;
  }
};
