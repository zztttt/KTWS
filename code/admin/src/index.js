import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Hello from './Mainlayout/Hello';
import Usermanage from './Mainlayout/Usermanage';
import Coursemanage from './Mainlayout/Coursemanage';
import {Route,HashRouter, Switch} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

ReactDOM.render((
	<HashRouter>
      <Switch>
        <Route path="/" component={Hello} exact/>
        <Route path="/Hello" component={Hello} />
        <Route path="/Usermanage" component={Usermanage} />
        <Route path="/Coursemanage" component={Coursemanage} />
      </Switch>
    </HashRouter>
), document.getElementById('root'));
