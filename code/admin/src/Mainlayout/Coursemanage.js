import React, { Component } from 'react';
import Sidebar from '../Bar/Asidebar';
import Headbar from '../Bar/Headbar';
import $ from 'jquery';
import { Table, Icon, Divider, Input, InputNumber, Popconfirm, Button, Modal, Form, Radio } from 'antd';
import 'antd/dist/antd.css';
import 'antd/lib/date-picker/style/css'; 
import PropTypes from 'prop-types';

var data=[{
	key:'1',
  course_id:"SE202",
  course_name:"asd",
  classroom_id:"1",
	user_id:1,
	total:20,
  time:"10:00-12:00"
},{
	course_id:"CS302",
  course_name:"zxcdd",
  classroom_id:"3",
  user_id:3,
  total:50,
  time:"08:00-10:00"
},{
  key:'3',
  course_id:"SE402",
  course_name:"aasdzxd",
  classroom_id:"1",
  user_id:1,
  total:30,
  time:"10:00-12:00"
},];


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
        title: 'courseID',
        dataIndex: 'course_id',
        width: '14%',
        editable: false,
      },
      {
        title: 'coursename',
        dataIndex: 'course_name',
        width: '14%',
        editable: false,
      },
      {
        title: 'teacher',
        dataIndex: 'user_id',
        width: '14%',
        editable: true,
      },
      {
        title: 'classroom',
        dataIndex: 'classroom_id',
        width: '14%',
        editable: true,
      },
      {
        title: 'numofstudent',
        dataIndex: 'total',
        width: '14%',
        editable: true,
      },
      {
        title: 'time',
        dataIndex: 'time',
        width: '14%',
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
      <div>
        <Table
          components={components}
          bordered
          dataSource={this.state.data}
          columns={columns}
          rowClassName="editable-row"
        />
      </div>
    );
  }
}
const AFormItem = Form.Item;

const CollectionCreateForm = Form.create()(
  class extends React.Component {
    render() {
      const { visible, onCancel, onCreate, form } = this.props;
      const { getFieldDecorator } = form;
      return (
        <Modal
          visible={visible}
          title="Create a new course"
          okText="Create"
          onCancel={onCancel}
          onOk={onCreate}
        >
          <Form layout="vertical">
            <AFormItem label="CourseID">
              {getFieldDecorator('CourseID', {
                rules: [{ required: true, message: 'Please input the courseID of course!' }],
              })(
                <Input />
              )}
            </AFormItem>
            <AFormItem label="Coursename">
              {getFieldDecorator('Coursename', {
                rules: [{ required: true, message: 'Please input the coursename of course!' }],})(<Input type="textarea" />)}
            </AFormItem>
            <AFormItem label="Teacher">
              {getFieldDecorator('Teacher', {
                rules: [{ required: true, message: 'Please input the teacher of course!' }],})(<Input type="textarea" />)}
            </AFormItem>
            <AFormItem label="Classroom">
              {getFieldDecorator('Classroom', {
                rules: [{ required: true, message: 'Please input the classroom of course!' }],})(<Input type="textarea" />)}
            </AFormItem>
            <AFormItem label="Numofstudent">
              {getFieldDecorator('Numofstudent', {
                rules: [{ required: true, message: 'Please input the number of students of course!' }],})(<Input type="textarea" />)}
            </AFormItem>
            <AFormItem label="Time">
              {getFieldDecorator('time', {
                rules: [{ required: true, message: 'Please input the time of course!' }],})(<Input type="textarea" />)}
            </AFormItem>
          </Form>
        </Modal>
      );
    }
  }
);

class Coursemanage extends Component {
  constructor(props){
    super(props);
    this.state = {
      visible: false,
    };
    /*var passeddata = this.props.location.username;
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
    }.bind(this));*/
  }

  showModal = () => {
    this.setState({ visible: true });
  }

  handleCancel = () => {
    this.setState({ visible: false });
  }

  handleCreate = () => {
    const form = this.formRef.props.form;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }

      console.log('Received values of form: ', values);
      form.resetFields();
      this.setState({ visible: false });
    });
  }

  saveFormRef = (formRef) => {
    this.formRef = formRef;
  }

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
              <button onClick={this.showModal}> Add&nbsp;courses</button>
              <CollectionCreateForm
                wrappedComponentRef={this.saveFormRef}
                visible={this.state.visible}
                onCancel={this.handleCancel}
                onCreate={this.handleCreate}
              />
              <EditableTable/>
            </div>
          </main>
        </div>
      </div>
      
    );
  }
}

export default Coursemanage;