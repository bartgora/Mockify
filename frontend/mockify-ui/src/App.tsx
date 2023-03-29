import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { HookView } from './components/hook-page-component';
import { Mainpage } from './components/main-page-component';

const App = () => {
  return (
    <React.StrictMode>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Mainpage />}></Route>
          <Route path="/hooks/:name" element={<HookView />}></Route>
        </Routes>
      </BrowserRouter>
    </React.StrictMode>
  );
};

export default App;
