import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import '../Bars/Sidebar.css';
import {Panel} from 'react-bootstrap';

class Report extends Component {
  constructor(props){
    super(props);
    this.getreport=this.getreport.bind(this);
  }
  getreport(){

  }
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar />
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <Panel bsStyle="info">
              <Panel.Heading>
                <Panel.Title componentClass="h3">课程名称：</Panel.Title>
              </Panel.Heading>
              <Panel.Body>
                Report
              </Panel.Body>
            </Panel>
          </main>
        </div>
      </div>
    );
  }
}

export default Report;
