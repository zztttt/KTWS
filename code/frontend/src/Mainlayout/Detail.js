import React, { Component } from 'react';
import Sidebar from '../Bars/Sidebar';
import Headbar from '../Bars/Headbar';
import '../Bars/Sidebar.css';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';

class Detail extends Component {
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar />
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div>
                <Content/>
            </div>
          </main>
        </div>
      </div>
    );
  }
}
function Content(){
  var classes = [{
      time:1,
  }, {
      time:2,
  }];
  return (
    <div className="row">
      <div className="col-lg-5">
        <h2>课程名称：</h2>
        <h2>aaa</h2>
        <BootstrapTable data={ classes }  striped={ true } pagination={true} search={ true } version='4'>
          <TableHeaderColumn dataField='time' align='center' width={'100%'} isKey={ true }>时间</TableHeaderColumn>
        </BootstrapTable>
      </div>
      <img src="http://3.pic.paopaoche.net/thumb/up/2018-2/201802091125424599775_600_0.png" alt="" className="col-lg-7"/>
    </div>
  );
}
export default Detail;
