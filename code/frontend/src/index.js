import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Sidebar from './Sidebar';
import Headbar from './Headbar';
import Chart from './Chart';

import registerServiceWorker from './registerServiceWorker';
import {Route,HashRouter, Switch} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
ReactDOM.render((
<div >
	<Headbar />
	<div class="row">
		<Sidebar/>
		<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
			<HashRouter>
			  <Switch>
			  	<Route path="/" component={Chart} />
			  </Switch>
			</HashRouter>
		</main>
	</div>
 </div>
), document.getElementById('root'));
registerServiceWorker();
