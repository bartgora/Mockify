import {combineReducers} from 'redux';
import {eventsReducer} from './eventsReducer';
import {Event} from '../actions';

export interface StoreState {
  events: Event[];
}

export default combineReducers({
  events: eventsReducer,
});
