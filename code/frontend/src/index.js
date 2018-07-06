import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Home from './Home/Home';
import Login from './Login/Login';
import registerServiceWorker from './registerServiceWorker';
import {Route,HashRouter, Switch} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
ReactDOM.render((
<div >
	<HashRouter>
	  <Switch>
	  	<Route path="/" component={Login} exact/>
	  	<Route path="/Home" component={Home} />
	  </Switch>
	</HashRouter>
 </div>
), document.getElementById('root'));
registerServiceWorker();
