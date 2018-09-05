import React, { Component } from 'react';
import './Sidebar.css';
import $ from 'jquery';

class Headbar extends Component {
  constructor(props){
    super(props);
    this.state={
      username:null
    }
    this.serverRequest = $.get("/getusername",function(data){
      this.setState({
           username: JSON.parse(data).username
        });
    }.bind(this));
  }
  render() {
      var str = window.location.href;
      console.log("href: " + str);
      var num = str.indexOf("0");
      console.log("num: "+ num);
      str = str.substr(0, num-1);
      console.log("str:"+str);
      var h = str+"8080/logout";
    return (
      <nav className="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        <a className="navbar-brand col-sm-3 col-md-2 mr-0" href="/">Menus</a>
        <a className="form-control-dark w-100">{this.state.username}</a>
          <ul className="navbar-nav px-3">
            <li className="nav-item text-nowrap">
              <a className="nav-link" href= { h }>Sign out</a>
            </li>
          </ul>
      </nav>
    );
  }
}

export default Headbar;
