import React, { Component } from 'react';
import './Login.css';
class Login extends Component {
	constructor(props) {
        super(props);
        this.state = {
        };
        this.loginbtn=this.loginbtn.bind(this);
    }
	loginbtn(){
		var path = {
			  pathname:'/Hello',
			}
		this.props.history.push(path);
	}
    render() {
    return (
		<form action="login.php" method="post">
            <div className="mycenter">
	            <form className="mysign" action="/user/login" method="post">
	                <div className="col-lg-11 text-center text-info">
	                    <h2>请登录</h2>
	                </div>
	                <div className="col-lg-10">
	                    <input type="text" className="form-control" name="username" placeholder="请输入账户名" required autoFocus/>
	                </div>
	                <div className="col-lg-10"></div>
	                <div className="col-lg-10">
	                    <input type="password" className="form-control" name="password" placeholder="请输入密码" required autoFocus/>
	                </div>
	                <div className="col-lg-10"></div>
	                <div className="col-lg-10 mycheckbox checkbox">
	                <div><input type="checkbox" className="col-lg-1"/>记住密码</div>
	                </div>
	                <div className="col-lg-10"></div>
	                <div className="col-lg-10">
	                    <button type="button" className="btn btn-success col-lg-12" onClick={this.loginbtn}>登录</button>
	                </div>
	            </form>
	        </div>
        </form>	
    );
  }
}

export default Login;
