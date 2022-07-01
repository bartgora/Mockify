import { EventsView } from "./components/EventView";

const App = () => {
  return (
    <div className="ui segment">
      <EventsView events={[]} />
    </div>
  );
};

export default App;
