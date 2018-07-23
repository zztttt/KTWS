import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import $ from 'jquery';
import { Table, Icon, Divider } from 'antd';
import 'antd/dist/antd.css';
import 'antd/lib/date-picker/style/css'; 
import PropTypes from 'prop-types';
class Chart extends Component {
  constructor(props){
    super(props);
    this.state = {
      classes: null,
    };
    this.serverRequest = $.get("/getclasses",function(data){
      console.log(data);
      this.setState({
           classes: JSON.parse(data),
        });
    }.bind(this));
  }

  static contextTypes={
    router:PropTypes.object
  }
  render() {

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
            <a onClick={function(){
                      var path = {  
                        pathname: '/Detail', 
                      }
                      localstorage.setItem('username', username);
                      var user = localstorage.getItem('username');
                      this.context.router.history.push(path);
                    }.bind(this)}>Action</a>
            <Divider type="vertical" />
            <a href="javascript:;">Delete</a>
            <Divider type="vertical" />
            <a href="javascript:;" className="ant-dropdown-link">
              More actions <Icon type="down" />
            </a>
          </span>
        ),
      }];
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar />
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div>
              <Table dataSource={this.state.classes} columns={columns} bordered></Table>
            </div>
          </main>
        </div>
      </div>
      
    );
  }
}

export default Chart;
