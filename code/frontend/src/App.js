import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
class App extends Component {
  render() {
    return (
      <canvas class="my-4 chartjs-render-monitor" id="myChart" width="1075" height="453" style="display: block; width: 1075px; height: 453px;"></canvas>
    );
  }
}

export default App;
