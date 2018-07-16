import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import $ from 'jquery';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
var classes = [{
      id: 1,
      classname: "class1",
      num: 120,
      frequency: 5,
      open:'Y'
  }, {
      id: 2,
      classname: "class2", 
      num: 8,
      frequency: 20,
      open:'N'
  }];
//const FrequencyTypes=[5,10,15,20];
class Table extends React.Component {
  constructor(props){
    super(props);
    this.getchart=this.getchart.bind(this);
    this.getchart();

  }
  getchart(){
    this.serverRequest = $.post("/getclasses",{name:JSON.stringify(this.props.username)},function(data){
          this.setState({
               classes: JSON.parse(data),
            });
    })
  }
  render() {
    /*const cellEditProp = {
      mode: 'dbclick',
    };*/
    return (
      <BootstrapTable data={ classes } /*cellEdit={ cellEditProp }*/ striped={ true } search={ true } version='4'>
      <TableHeaderColumn dataField='id'  width={'10%'} isKey={ true }>课程号</TableHeaderColumn>
      <TableHeaderColumn dataField='classname'  width={'10%'}>课程名</TableHeaderColumn>
      <TableHeaderColumn dataField='num'  width={'10%'}>总人数</TableHeaderColumn>
      <TableHeaderColumn dataField='frequency' /*editable={ { type: 'select', options:{ values: FrequencyTypes }}}*/width={'10%'}>拍照频率（秒）</TableHeaderColumn>
      <TableHeaderColumn dataField='open' /*editable={ { type: 'checkbox', options: { values: 'Y:N' } } }*/ width={'10%'}>开启</TableHeaderColumn>
      <TableHeaderColumn dataField='btn' width={'10%'}></TableHeaderColumn>      
      </BootstrapTable>
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
              <Table username={this.state.username}/>
            </div>
          </main>
        </div>
      </div>
      
    );
  }
}

export default Chart;
