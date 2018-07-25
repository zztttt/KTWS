import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import {Panel} from 'react-bootstrap';
import { DatePicker } from 'antd';
import $ from 'jquery';

var ReactHighcharts = require('react-highcharts');

var config1 = {
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
      '1', '2', '3', '4', '5', '6']
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
      name: '出勤率', 
      data: [7,8,9,11,2,5]
    }, {
      name: '专注率', 
      data: [5,4,8,10,9,3]
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
    this.state = {
      config:config2
    };
    this.getstatistics=this.getstatistics.bind(this);
    this.change=this.change.bind(this);
  }
  getstatistics(){

  }
  change(){
    this.setState({
      config:config2
    })
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
              <button onClick={this.change}/>
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
      date:null,
      dayStatistics:null,
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
    }.bind(this));

    this.setState({
      num:this.state.dayStatistics[0],
      config1:config1
    })
  }
  render(){
    const dateFormat = 'YYYY/MM/DD';
    return (
      <div>
        <DatePicker value={this.state.date} onChange={this.handleChange} format={dateFormat} />


        <Panel bsStyle="info">
          <Panel.Heading>
            <Panel.Title componentClass="h3">{this.state.num}</Panel.Title>
          </Panel.Heading>
          <Panel.Body>
            <div className="row">
              <div className="col-lg-5">
                <ReactHighcharts config={this.state.config1}></ReactHighcharts>
              </div>
              <div className="col-lg-5">
                <ReactHighcharts config={config2}></ReactHighcharts>
              </div>
            </div>
          </Panel.Body>
        </Panel>
      </div>
    );
  }
}
export default Statistics;
