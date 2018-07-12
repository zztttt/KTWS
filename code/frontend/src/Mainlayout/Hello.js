import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import '../Bars/Sidebar.css';
import {Panel} from 'react-bootstrap';

var ReactHighcharts = require('react-highcharts');

var config = {
  title: {
    text: '每日上课听讲人数'
  },
  subtitle: {
    text: '课堂威视'
  },
  xAxis: {
    title: {
      text: 'Day'
    },
    categories: [
      'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  tooltip: {
    valueSuffix: '人'
  },
  legend: {
    layout: 'vertical', 
    align: 'right', 
    verticalAlign: 'middle', 
    borderWidth: 0
  },
  series: [
    {
      name: 'math', 
      data: [7,8,9,11,2,5,6]
    }, {
      name: 'Chinese', 
      data: [5,4,8,10,9,3,7]
    }],
}
class Hello extends Component {
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
                <ReactHighcharts config={config}></ReactHighcharts>
              </Panel.Body>
            </Panel>
          </main>
        </div>
      </div>
    );
  }
}

export default Hello;
