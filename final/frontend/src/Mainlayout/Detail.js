import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import '../Bars/Sidebar.css';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import {Panel} from 'react-bootstrap';
import $ from 'jquery';

var ReactHighcharts = require('react-highcharts');
var atmosphere = ["非常积极","积极","略显消极"];
var suggestion = ["课堂情况非常好，同学们都爱听您的课，请您继续努力！","上课效果较好，同学们大都认真听讲，请您适当关注课堂氛围，调整教学安排！","最近的课效果不是很理想，同学们兴趣不很高，请您适当调整上课方式与节奏，提高同学们的兴趣！"];
var config1 = {
  title: {
    text: '近30张照片统计'
  },
  subtitle: {
    text: '课堂威视'
  },
  xAxis: {
    title: {
      text: ''
    },
    categories: [
      '30~20', '20~10', '10~0']
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
      name: '总人数', 
      data: [2,5,6]
    }, {
      name: '专注人数', 
      data: [9,3,7]
    }],
}
var config2 = {
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
var config3 = {
  title: {text: '单张照片统计'},
  subtitle: {
    text: '课堂威视'
  },
  xAxis: {
    title: {
      text: ''
    }
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
class Detail extends Component {
  constructor(props){
    super(props);
    this.state = {
      classname:localStorage.getItem('classname')
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
          <Sidebar/>
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
      num:0,
      config1:config1,
      config2:config2,
      config3:config3,
      atmosphere:[0,0],
      atmospherenum:0
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
           num:JSON.parse(data)[0]
        });
      config1.series[0].data[0] = this.state.lineChartData[1];
      config1.series[0].data[1] = this.state.lineChartData[3];
      config1.series[0].data[2] = this.state.lineChartData[5];
      config1.series[1].data[0] = this.state.lineChartData[2];
      config1.series[1].data[1] = this.state.lineChartData[4];
      config1.series[1].data[2] = this.state.lineChartData[6];
    }.bind(this));
    this.serverRequest = $.post("/getAtmosphere",{coursename:this.props.classname},function(data){
      console.log("getAtmosphere="+data);
      this.setState({
           atmosphere: JSON.parse(data),
        });
      if(this.state.atmosphere[0]>0.8&&this.state.atmosphere[1]>0.8){
        this.setState({
           atmospherenum: 0,
        });
      }
      else if(this.state.atmosphere[0]<0.6&&this.state.atmosphere[1]<0.6){
        this.setState({
           atmospherenum: 2,
        });
      }
      else{
        this.setState({
           atmospherenum: 1,
        });
      }
    }.bind(this));
  }
  onRowClick(row){
    config2.series[0].data[0][1] = (row.focus);
    config2.series[0].data[1][1] = (row.num-row.focus);
    config2.series[0].data[2][1] = (this.state.totalnum-row.num);
    config3.series[0].data[0] = (row.focus);
    config3.series[1].data[0] = (row.num-row.focus);
    config3.series[2].data[0] = (this.state.totalnum-row.num);
    this.setState({
      config2:config2,
      config3:config3,
    })
    this.serverRequest = $.post("/getImage",{id:row.id},function(data){
      console.log("getImage="+data);
      this.setState({
           imageData: data,
        });
    }.bind(this));
  }
  render(){
    const options={
      onRowClick: this.onRowClick
    }
    return (
      <div>
        <Panel bsStyle="info">
          <Panel.Heading>
            <Panel.Title componentClass="h3">课程名称：{this.props.classname}</Panel.Title>
          </Panel.Heading>
          <Panel.Body>
            <BootstrapTable  className="col-lg-6" data={ this.state.photoInfo } options={options} align={"center"} striped={ true } pagination={true} search={ true } version='4'>
              <TableHeaderColumn dataField='id' dataAlign='center'  isKey={ true }>Id</TableHeaderColumn>
              <TableHeaderColumn dataField='filename' hidden dataAlign='center' isKey={ false }>文件名</TableHeaderColumn>
              <TableHeaderColumn dataField='time' dataAlign='center'  isKey={ false }>时间</TableHeaderColumn>
              <TableHeaderColumn dataField='num' dataAlign='center'  isKey={ false }>人数</TableHeaderColumn>
              <TableHeaderColumn dataField='focus' dataAlign='center'  isKey={ false }>专注人数</TableHeaderColumn>
            </BootstrapTable>
            <img src={"data:image/png;base64," + this.state.imageData} alt="" className="col-lg-6"/>
          </Panel.Body>
        </Panel>
        <Panel bsStyle="info">
          <Panel.Heading>
            <Panel.Title componentClass="h3">单张照片统计：</Panel.Title>
          </Panel.Heading>
          <Panel.Body>
            <div className="col-lg-7">
              <ReactHighcharts config={this.state.config3}></ReactHighcharts>
            </div>
            <div className="col-lg-5">
              <ReactHighcharts config={this.state.config2}></ReactHighcharts>
            </div>
          </Panel.Body>
        </Panel>
        <Panel bsStyle="info">
            <Panel.Heading>
              <Panel.Title componentClass="h3">近{this.state.num}张照片统计：</Panel.Title>
            </Panel.Heading>
            <Panel.Body>
              <ReactHighcharts className="col-lg-12" config={this.state.config1}></ReactHighcharts>
            </Panel.Body>
        </Panel>
        <Panel bsStyle="info">
            <Panel.Heading>
              <Panel.Title componentClass="h3">课堂氛围及建议：</Panel.Title>
            </Panel.Heading>
            <Panel.Body>
              <h2>尊敬的老师，你{this.props.classname}课的平均出勤率为{(this.state.atmosphere[0]*100).toFixed(2)}%，平均专注听课率为{(this.state.atmosphere[1]*100).toFixed(2)}%，课堂氛围{atmosphere[this.state.atmospherenum]},老师您{suggestion[this.state.atmospherenum]}</h2>
            </Panel.Body>
        </Panel>
      </div>
    );
  }
}
export default Detail;
