import React from 'react';
import { Event } from '../actions';
import style from './components.module.scss';

interface Props {
  event: Event;
}
export default function EventPanel(props: Props) {
  const response = JSON.stringify(props.event.response.body, null, 4);
  const request = JSON.stringify(props.event.request.body, null, 4);

  return (
    <div className={style.item}>
      <div className="content">
        <div className="header">
          Method: {props.event.request.method}, Date: {props.event.timestamp}
        </div>
        <div className="content">
          <pre>{request}</pre>
        </div>
        <div className="content">
          <pre>{response}</pre>
        </div>
        <div className="ui divider"></div>
      </div>
    </div>
  );
}
