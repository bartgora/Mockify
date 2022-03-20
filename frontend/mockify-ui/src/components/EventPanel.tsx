import React from 'react';
import {Event} from '../actions';

interface Props {
  event: Event;
}
class EventPanel extends React.Component<Props, Props> {
  render(): React.ReactNode {
    const content = JSON.stringify(this.props.event.response.body);
    return (
      <div className="item">
        <div className="content">
          <div className="header">
            Method: {this.props.event.request.method}, Date: {this.props.event.timestamp}
          </div>
          <div className="content">{content}</div>
          <div className="ui divider"></div>
        </div>
      </div>
    );
  }
}

export default EventPanel;
