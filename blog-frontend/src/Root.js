import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import App from './components/App.js';
import { Provider } from 'react-redux';
import configure from './store/configure.js';
import { Route } from 'react-router-dom';

const store = configure();

const Root = () => {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Route path="/" component={App} />
      </BrowserRouter>
    </Provider>
  );
}

export default Root;