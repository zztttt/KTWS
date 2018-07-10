import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
var ReactHighcharts = require('react-highcharts');
var Highcharts = require('highcharts');

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
class Statistics extends Component {
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar />
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div>
              <ReactHighcharts config={config}></ReactHighcharts>
            </div>
          </main>
        </div>
      </div>
    );
  }
}

export default Statistics;
