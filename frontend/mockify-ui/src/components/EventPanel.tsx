import React from "react";
import { Event } from "../actions";

interface Props {
  event: Event;
}
class EventPanel extends React.Component<Props, Props> {
  render(): React.ReactNode {
    return (
      <div className="item">
        <div className="content">
          <div className="header">
            Method: {this.props.event.request.method}
          </div>
          <div className="meta">{this.props.event.response.body}</div>
        </div>
      </div>
    );
  }
}

export default EventPanel;
