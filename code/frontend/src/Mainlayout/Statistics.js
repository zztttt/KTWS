import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
class Statistics extends Component {
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar />
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div>
              <div>Statistics</div>
            </div>
          </main>
        </div>
      </div>
    );
  }
}

export default Statistics;
