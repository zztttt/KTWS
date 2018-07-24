import React, { Component } from 'react';
import Sidebar from '../Bar/Asidebar';
import Headbar from '../Bar/Headbar';
import $ from 'jquery';
import { Table, Icon, Divider, Input, InputNumber, Popconfirm, Form } from 'antd';
import 'antd/dist/antd.css';
import 'antd/lib/date-picker/style/css'; 
import PropTypes from 'prop-types';

var data=[{
	key:'1',
	user_id:1,
	username:"FJJ",
	password:"123"
},{
	key:'2',
	user_id:2,
	username:"ZZT",
	password:"223"
}];


const FormItem = Form.Item;
const EditableContext = React.createContext();

const EditableRow = ({ form, index, ...props }) => (
  <EditableContext.Provider value={form}>
    <tr {...props} />
  </EditableContext.Provider>
);

const EditableFormRow = Form.create()(EditableRow);

class EditableCell extends React.Component {
  getInput = () => {
    if (this.props.inputType === 'number') {
      return <InputNumber />;
    }
    return <Input />;
  };

  render(){
    const {
      editing,
      dataIndex,
      title,
      inputType,
      record,
      index,
      ...restProps
    } = this.props;
    return (
      <EditableContext.Consumer>
        {(form) => {
          const { getFieldDecorator } = form;
          return (
            <td {...restProps}>
              {editing ? (
                <FormItem style={{ margin: 0 }}>
                  {getFieldDecorator(dataIndex, {
                    rules: [{
                      required: true,
                      message: `Please Input ${title}!`,
                    }],
                    initialValue: record[dataIndex],
                  })(this.getInput())}
                </FormItem>
              ) : restProps.children}
            </td>
          );
        }}
      </EditableContext.Consumer>
    );
  }
}

class EditableTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = { data, editingKey: '' };
    this.columns = [
      {
        title: 'Userid',
        dataIndex: 'user_id',
        width: '25%',
        editable: false,
      },
      {
        title: 'Username',
        dataIndex: 'username',
        width: '25%',
        editable: true,
      },{
        title: 'Password',
        dataIndex: 'password',
        width: '25%',
        editable: true,
      },
      
      {
        title: 'operation',
        dataIndex: 'operation',
        render: (text, record) => {
          const editable = this.isEditing(record);
          return (
            <div>
              {editable ? (
                <span>
                  <EditableContext.Consumer>
                    {form => (
                      <a
                        href="javascript:;"
                        onClick={() => this.save(form, record.key)}
                        style={{ marginRight: 8 }}
                      >
                        Save
                      </a>
                    )}
                  </EditableContext.Consumer>
                  <Popconfirm
                    title="Sure to cancel?"
                    onConfirm={() => this.cancel(record.key)}
                  >
                    <a>Cancel</a>
                  </Popconfirm>
                </span>
              ) : (
                <a onClick={() => this.edit(record.key)} href="javascript:;">Edit</a>
              )}
              <Divider type="vertical" />
              <span>
                <Popconfirm title="Sure to delete?" onConfirm={() => this.handleDelete(record.key)}>
                  <a href="javascript:;">Delete</a>
                </Popconfirm>
              </span>
            </div>
          );
        },
      },
    ];
  }
  handleDelete = (key) => {
    const data = [...this.state.data];
    this.setState({ data: data.filter(item => item.key !== key) });
  }

  isEditing = (record) => {
    return record.key === this.state.editingKey;
  };

  edit(key) {
    this.setState({ editingKey: key });
  }

  save(form, key) {
    form.validateFields((error, row) => {
      if (error) {
        return;
      }
      const newData = [...this.state.data];
      const index = newData.findIndex(item => key === item.key);
      if (index > -1) {
        const item = newData[index];
        newData.splice(index, 1, {
          ...item,
          ...row,
        });
        this.setState({ data: newData, editingKey: '' });
      } else {
        newData.push(row);
        this.setState({ data: newData, editingKey: '' });
      }
    });
  }

  cancel = () => {
    this.setState({ editingKey: '' });
  };

  render() {
    const components = {
      body: {
        row: EditableFormRow,
        cell: EditableCell,
      },
    };

    const columns = this.columns.map((col) => {
      if (!col.editable) {
        return col;
      }
      return {
        ...col,
        onCell: record => ({
          record,
          dataIndex: col.dataIndex,
          title: col.title,
          editing: this.isEditing(record),
        }),
      };
    });

    return (
      <Table
        components={components}
        bordered
        dataSource={this.state.data}
        columns={columns}
        rowClassName="editable-row"
      />
    );
  }
}

class Usermanage extends Component {
  /*constructor(props){
    super(props);
    var passeddata = this.props.location.username;
    var username = passeddata;
    this.state = {
      classes: null,
      username:username,
    };
    this.serverRequest = $.post("/getclasses",{name:this.state.username},function(data){
      console.log(data);
      this.setState({
           classes: JSON.parse(data),
        });
    }.bind(this));
  }*/

  static contextTypes={
    router:PropTypes.object
  }
  render() {
    return (
      <div>
        <Headbar />
        <div className="row">
          <Sidebar username="LQY"/*{this.state.username}*//>
          <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div>
              <EditableTable/>
            </div>
          </main>
        </div>
      </div>
      
    );
  }
}

export default Usermanage;