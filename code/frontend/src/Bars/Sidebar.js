import React, { Component } from 'react';
import './Sidebar.css';
import PropTypes from 'prop-types';
class Sidebar extends Component {
  constructor(props){
    super(props);
  }
  static contextTypes={
    router:PropTypes.object
  }
  render() {
    return (
      <nav className="col-md-2 d-none d-md-block bg-light sidebar">
          <div className="sidebar-sticky">
            <ul className="nav flex-column">
              <li className="nav-item">
                <a className="nav-link active" onClick={function(){
                  var path = {  
                    pathname: '/Hello', 
                    username:this.state.username, 
                  }
                  this.context.router.history.push(path);
                }.bind(this)}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
                  Home <span className="sr-only">(current)</span>
                </a>
              </li>
              <li className="nav-item">
                <a className="nav-link" onClick={function(){
                  var path = {  
                    pathname: '/Chart', 
                    username:this.state.username, 
                  }
                  this.context.router.history.push(path);
                }.bind(this)}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                  Classes
                </a>
              </li>
              <li className="nav-item">
                <a className="nav-link" onClick={function(){
                  var path = {  
                    pathname: '/Statistics', 
                    username:this.state.username, 
                  }
                  this.context.router.history.push(path);
                }.bind(this)} >
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
                  Statistics
                </a>
              </li>
              <li className="nav-item">
                <a className="nav-link" onClick={function(){
                  var path = {  
                    pathname: '/Report', 
                    username:this.state.username, 
                  }
                  this.context.router.history.push(path);
                }.bind(this)}>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="feather feather-bar-chart-2"><line x1="18" y1="20" x2="18" y2="10"></line><line x1="12" y1="20" x2="12" y2="4"></line><line x1="6" y1="20" x2="6" y2="14"></line></svg>
                  Reports
                </a>
              </li>
            </ul>
          </div>
        </nav>
    );
  }
}

export default Sidebar;
