import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Layout from './components/layout/Layout';
import Home from './components/home/Home';
import Registration from './components/Registration';
import Login from './components/Login';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path='/' element={<Layout />}>
          <Route path='/' element={<Home />} />
          <Route path='/registration' element={<Registration />}/>
          <Route path='/login' element={<Login />}/>
        </Route>
      </Routes>
    </div>
  );
}

export default App;
