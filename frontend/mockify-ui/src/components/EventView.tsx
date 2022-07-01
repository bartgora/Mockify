import React from 'react';
import { Event } from '../actions';
import EventPanel from './EventPanel';

interface Props {
  events: Event[];
}

export function EventsView(props: Props) {
  if (!props.events.length) {
    return <div>Loading...</div>;
  }
  let index = 0;
  return (
    <>
      <div className="ui header">Events</div>
      <div className="ui divider"></div>
      {props.events.map((event) => {
        return (
          <div key={index++} className="ui items">
            <EventPanel event={event} />
          </div>
        );
      })}
    </>
  );
}
