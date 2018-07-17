import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import $ from 'jquery';
import { Table, Icon, Divider } from 'antd';
import 'antd/dist/antd.css';
import 'antd/lib/date-picker/style/css'; 
var classes = [{
      key:'1',
      id: 1,
      classname: "class1",
      num: 120,
      frequency: 5,
      open:'Y'
  }, {
      key:'2',
      id: 2,
      classname: "class2", 
      num: 8,
      frequency: 20,
      open:'N'
  }];

var columns=[{
    title:'课程号',
    dataIndex:'id',
  },{
    title:'课程名',
    dataIndex:'classname',
  },{
    title:'总人数',
    dataIndex:'num',
  },{
    title:'拍摄频率（秒）',
    dataIndex:'frequency',
  },{
    title:'开启',
    dataIndex:'open',
  },{
    title: 'Action',
    key: 'action',
    render: (text, record) => (
      <span>
        <a href="javascript:;">Action{record.name}</a>
        <Divider type="vertical" />
        <a href="javascript:;">Delete</a>
        <Divider type="vertical" />
        <a href="javascript:;" className="ant-dropdown-link">
          More actions <Icon type="down" />
        </a>
      </span>
    ),
  }];
//const FrequencyTypes=[5,10,15,20];
class Content extends React.Component {
  constructor(props){
    super(props);
    //this.getchart=this.getchart.bind(this);
    this.state = {
      classes: []
    };
    this.serverRequest = $.post("/getclasses",{name:this.props.username},function(data){
      this.setState({
           classes: JSON.parse(data),
        });
    }.bind(this));
    console.log(this.state.classes);
  }

  render() {
    /*const cellEditProp = {
      mode: 'dbclick',
    };*/
    return (
      <Table dataSource={classes} columns={columns} bordered></Table>
    );
  }
}
class Chart extends Component {
  constructor(props){
    super(props);
    var passeddata = this.props.location.username;
    var username = passeddata;
    this.state = {
      classes: null,
      username:username,
    };
  }
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar username={this.state.username}/>
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div>
              <Content username={this.state.username}/>
            </div>
          </main>
        </div>
      </div>
      
    );
  }
}

export default Chart;
