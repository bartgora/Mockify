import React from 'react';
import {Event} from '../actions';

interface Props {
  event: Event;
}
class EventPanel extends React.Component<Props, Props> {
  render(): React.ReactNode {
    return (
      <div className="ui item">
        <div className="ui content">
          <div className="ui header">
            Method: {this.props.event.request.method}, Date: {this.props.event.timestamp}
          </div>
          <div className=" ui content">{JSON.stringify(this.props.event.response.body)}</div>
          <div className="ui divider"></div>
        </div>
      </div>
    );
  }
}

export default EventPanel;
