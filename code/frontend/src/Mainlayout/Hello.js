import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import '../Bars/Sidebar.css';
import $ from 'jquery';
class Hello extends Component {
  constructor(props){
    super(props);
    this.state = {
      username:null
    };
    this.serverRequest = $.get("/getusername",function(data){
      this.setState({
           username: JSON.parse(data).username
        });
    }.bind(this));
  }
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar/>
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <h1>欢迎使用课堂威视，{this.state.username}</h1>
          </main>
        </div>
      </div>
    );
  }
}

export default Hello;
