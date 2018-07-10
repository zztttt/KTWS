import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import '../Bars/Sidebar.css';

class Detail extends Component {
  constructor(){
    super(props);
  }
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar />
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div>
                <div>Detail</div>
            </div>
          </main>
        </div>
      </div>
    );
  }
}

export default Detail;
