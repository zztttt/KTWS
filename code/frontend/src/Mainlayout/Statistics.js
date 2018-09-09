import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import {Panel} from 'react-bootstrap';
import { DatePicker } from 'antd';
import $ from 'jquery';

var ReactHighcharts = require('react-highcharts');

var config1 = {
  title: {
    text: '单日上课听讲人数'
  },
  subtitle: {
    text: '课堂威视'
  },
  xAxis: {
    title: {
      text: ''
    },
    categories: [
      '1', '2', '3', '4', '5', '6']
  },
  yAxis: {
    title: {
      text: '人'
    }
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
      name: '出勤人数', 
      data: [0,0,0,0,0,0]
    }, {
      name: '专注人数', 
      data: [0,0,0,0,0,0]
    }],
}
var config2 = {
  subtitle: {
    text: '课堂威视'
  },
  xAxis: {
    title: {
      text: 'groups'
    }
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
  series: [{
        type: 'column',
        name: '专注人数',
        data: [
          35
        ]
      },{
        type: 'column',
        name: '不专注人数',
        data: [
          35
        ]
      },{
        type: 'column',
        name: '未识别出人数',
        data: [
          35
        ]
      }],
} 
class Statistics extends Component {
  constructor(props){
    super(props);
  }
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar/>
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div>
              <Content/>
            </div>
          </main>
        </div>
      </div>
    );
  }
}
class Content extends Component{
  constructor(props) {
    super(props);
    this.state = { 
      value: '',
      dayStatistics:[0,0,0,0,0,0,0,0,0,0,0,0,0],
      config1:config1,
      num:0
     };
    this.handleChange = this.handleChange.bind(this);
  }
  handleChange(date, dateString) {
    this.serverRequest = $.post("/getStatisticsByDay",{date:dateString},function(data){
      console.log(data);
      this.setState({
           dayStatistics: JSON.parse(data),
        });
      for(var i=0;i<6;i++){
        config1.series[0].data[i] = this.state.dayStatistics[2*i+1];
        config1.series[1].data[i] = this.state.dayStatistics[2*i+2];
      }
      config1.title.text = dateString + "日课堂情况统计";
      this.setState({
        num:this.state.dayStatistics[0],
        config1:config1
      })
    }.bind(this));
  }
  render(){
    const dateFormat = 'YYYY/MM/DD';
    return (
      <div>
        <DatePicker onChange={this.handleChange} format={dateFormat} />
        <Panel bsStyle="info">
          <Panel.Heading>
            <Panel.Title componentClass="h3">{this.state.num}</Panel.Title>
          </Panel.Heading>
          <Panel.Body>
            <div className="row">
              <div className="col-lg-12">
                <ReactHighcharts config={this.state.config1}></ReactHighcharts>
              </div>
            </div>
          </Panel.Body>
        </Panel>
      </div>
    );
  }
}
export default Statistics;
