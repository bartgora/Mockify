import React from "react";
import { connect } from "react-redux";
import { Event, fetchData } from "../actions";
import { StoreState } from "../reducers";
import EventPanel from "./EventPanel";

interface Props {
  events: Event[];
  fetchData: Function;
}

class _EventsView extends React.Component<Props> {
  componentDidMount() {
    const path = window.location.pathname;
    this.props.fetchData(path);
  }
  render() {
    if (!this.props.events.length) {
      return <div>Loading...</div>;
    }
    let index = 0;
    return (
      <>
        <div className="ui header">Events</div>
        <div className="ui ">
          {this.props.events.map((event) => {
            return (
              <div key={index++} className="item">
                <EventPanel event={event} />
              </div>
            );
          })}
        </div>
      </>
    );
  }
}

const mapStateToProps = (state: StoreState): { events: Event[] } => {
  return { events: state.events };
};

export const EventsView = connect(mapStateToProps, { fetchData })(_EventsView);
