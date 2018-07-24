import React, { Component } from 'react';
import Sidebar from '../Bar/Asidebar';
import Headbar from '../Bar/Headbar';
import '../Bar/Sidebar.css';
import {Panel} from 'react-bootstrap';
import $ from 'jquery';
class Hello extends Component {
  /*constructor(props){
    super(props);
    this.state = {
      username:null
    };
    this.serverRequest = $.get("/getusername",function(data){
      this.setState({
           username: JSON.parse(data).username
        });
    }.bind(this));
  }*/
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar username="LQY"/*{this.state.username}*//>
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <h1>欢迎使用课堂威视</h1>
          </main>
        </div>
      </div>
    );
  }
}

export default Hello;
