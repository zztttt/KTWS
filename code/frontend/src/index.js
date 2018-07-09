import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Hello from './Mainlayout/Hello';
import Chart from './Mainlayout/Chart';
import Statistics from './Mainlayout/Statistics';
import Login from './Login/Login';
import registerServiceWorker from './registerServiceWorker';
import {Route,HashRouter, Switch} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
ReactDOM.render((
<div >
	<HashRouter>
	  <Switch>
	  	<Route path="/" component={Login} exact/>
	  	<Route path="/Hello" component={Hello} />
        <Route path="/Chart" component={Chart} />
        <Route path="/Statistics" component={Statistics} />
	  </Switch>
	</HashRouter>
 </div>
), document.getElementById('root'));
registerServiceWorker();
