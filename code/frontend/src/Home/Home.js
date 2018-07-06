import React, { Component } from 'react';
import Sidebar from './Sidebar';
import Headbar from './Headbar';
import Chart from './Chart';
import Login from '../Login/Login';
import {Route,HashRouter, Switch} from 'react-router-dom';
class Home extends Component {
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar/>
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <HashRouter>
              <Switch>
                <Route path="/" component={Chart} />
              </Switch>
            </HashRouter>
          </main>
        </div>
      </div>
    );
  }
}

export default Home;
