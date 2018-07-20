import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import '../Bars/Sidebar.css';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import {Panel} from 'react-bootstrap';
import $ from 'jquery';

var ReactHighcharts = require('react-highcharts');
var config1 = {
  title: {
    text: '近30张照片统计'
  },
  subtitle: {
    text: '课堂威视'
  },
  xAxis: {
    title: {
      text: 'Day'
    },
    categories: [
      '30~20', '20~10', '10~0']
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
      name: '总人数', 
      data: [2,5,6]
    }, {
      name: '专注人数', 
      data: [9,3,7]
    }],
}
const config2 = {
      title: {text: '单张照片统计'},
      subtitle: {text: '课堂威视'},
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}%</b>: {point.percentage:.1f} %',
            style: {
               color: (ReactHighcharts.theme && ReactHighcharts.theme.contrastTextColor) || 'black'
            }
          }
        }
      },
      series: [{
        type: 'pie',
        name: 'Browser share',
        data: [
          ['识别出专注人数',   35.0],
          ['识别出不专注人数',   35.0],
          ['未识别出人数',  30],
        ]
      }],
    } 
class Detail extends Component {
  constructor(props){
    super(props);
    var username = this.props.location.username;
    var classname = this.props.location.classname;
    console.log(classname);
    this.state = {
      username:username,
      classname:classname
    };
    this.getdetail=this.getdetail.bind(this);
  }
  getdetail(){

  }
  render() {
    return (
      <div>
        <Headbar className="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow"/>
        <div className="row">
          <Sidebar username={this.state.username}/>
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div>
                <Content classname={this.state.classname}/>
            </div>
          </main>
        </div>
      </div>
    );
  }
}
class Content extends Component{
  constructor(props){
    super(props);
    this.onRowClick=this.onRowClick.bind(this);
    this.state = {
      showImgAddr:null,
      config1:config1,
      config2:config2
    };
    this.serverRequest = $.post("/getphotos",{name:this.props.classname},function(data){
      console.log(data);
      this.setState({
           photoInfo: JSON.parse(data),
        });
    }.bind(this));
    this.serverRequest = $.post("/gettotalnum",{name:this.props.classname},function(data){
      console.log("totnum="+data);
      this.setState({
           totalnum: JSON.parse(data),
        });
    }.bind(this));
    this.serverRequest = $.post("/getRecentStatistic",{name:this.props.classname},function(data){
      console.log("getRecentStatistic="+data);
      this.setState({
           lineChartData: JSON.parse(data),
        });
    }.bind(this));
  }
  onRowClick(row){
    config1.series[0].data[0] = this.state.lineChartData[0];
    config1.series[0].data[1] = this.state.lineChartData[2];
    config1.series[0].data[2] = this.state.lineChartData[4];
    config1.series[1].data[0] = this.state.lineChartData[1];
    config1.series[1].data[1] = this.state.lineChartData[3];
    config1.series[1].data[2] = this.state.lineChartData[5];
    config2.series[0].data[0][1] = (row.focus);
    config2.series[0].data[1][1] = (row.num-row.focus);
    config2.series[0].data[2][1] = (this.state.totalnum-row.num);
    this.setState({
      config1:config1,
      config2:config2,
      showImgAddr: row.filename
    })
  }
  render(){
    const options={
      onRowClick: this.onRowClick
    }
    return (
      <div>
        <Panel bsStyle="info">
          <Panel.Heading>
            <Panel.Title componentClass="h3">课程名称：</Panel.Title>
          </Panel.Heading>
          <Panel.Body>
            <BootstrapTable  className="col-lg-6" data={ this.state.photoInfo } options={options} align={"center"} striped={ true } pagination={true} search={ true } version='4'>
              <TableHeaderColumn dataField='id' dataAlign='center'  isKey={ true }>Id</TableHeaderColumn>
              <TableHeaderColumn dataField='filename' hidden dataAlign='center' isKey={ false }>文件名</TableHeaderColumn>
              <TableHeaderColumn dataField='time' dataAlign='center'  isKey={ false }>时间</TableHeaderColumn>
              <TableHeaderColumn dataField='num' dataAlign='center'  isKey={ false }>人数</TableHeaderColumn>
              <TableHeaderColumn dataField='focus' dataAlign='center'  isKey={ false }>专注人数</TableHeaderColumn>
            </BootstrapTable>
            <img src={this.state.showImgAddr} alt="" className="col-lg-6"/>
          </Panel.Body>
        </Panel>
        <Panel bsStyle="info">
          <Panel.Heading>
            <Panel.Title componentClass="h3">课程名称：</Panel.Title>
          </Panel.Heading>
          <Panel.Body>
            <div className="col-lg-7">
              <ReactHighcharts config={config1}></ReactHighcharts>
            </div>
            <div className="col-lg-5">
              <ReactHighcharts config={this.state.config2}></ReactHighcharts>
            </div>
          </Panel.Body>
        </Panel>
        <Panel bsStyle="info">
            <Panel.Heading>
              <Panel.Title componentClass="h3">近30张照片统计：</Panel.Title>
            </Panel.Heading>
            <Panel.Body>
              <ReactHighcharts className="col-lg-12" config={this.state.config1}></ReactHighcharts>
            </Panel.Body>
        </Panel>
      </div>
    );
  }
}
export default Detail;
