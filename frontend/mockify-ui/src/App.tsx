import { EventsView } from "./components/EventView";

const App = () => {
  return (
    <>
      <div className="ui segment">
        <div className="ui header">Mockify</div>
        <p>
          GET: /hook/[HOOKNAME] - creates hook
          <br />
          GET: /hook/[HOOKNAME]/events - get all responses
          <br />
          PATCH /hook/[HOOKNAME]/response - change response json returned on
          call
          <br />
          "Supported Methods: GET, POST, PUT, PATCH,DELETE"
          <br />
        </p>
      </div>
      <div className="ui segment">
        <EventsView />
      </div>
    </>
  );
};

export default App;
