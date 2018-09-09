import React, { Component } from 'react';
import './Sidebar.css';
class Headbar extends Component {
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
        <input className="form-control-dark w-100" type="text" placeholder="Search" aria-label="Search"/>
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