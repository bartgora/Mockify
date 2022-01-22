import React from 'react';
import {Event} from '../actions';

interface Props {
  event: Event;
}
class EventPanel extends React.Component<Props, Props> {
  render(): React.ReactNode {
    return <div className="content">Method: {this.props.event.request.method}</div>;
  }
}

export default EventPanel;
