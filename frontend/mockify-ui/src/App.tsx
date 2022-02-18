import { Container, Header } from "semantic-ui-react";

const App = () => {
  return (
    <>
      <Container text>
        <Header as="h2"></Header>
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
      </Container>
    </>
  );
};

export default App;
