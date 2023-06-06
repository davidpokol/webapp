import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Layout from './components/layout/Layout';
import Home from './components/home/Home';
import Registration from './components/auth/Registration';
import Login from './components/auth/Login';
import ProfilePage from './components/ProfilePage';
import CreateRecipe from './components/CreateRecipe';
import RecipePage from './components/recipes/RecipePage';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path='/' element={<Layout />}>
          <Route path='/' element={<Home />} />
          <Route path='/registration' element={<Registration />}/>
          <Route path='/login' element={<Login />}/>
          <Route path='/:username' element={<ProfilePage />}/>
          <Route path='/create' element={<CreateRecipe />}/>
          <Route path='/recipe/:id' element={<RecipePage />}/>
          <Route path='/recipe/:id/update' element={<CreateRecipe />}/>
        </Route>
      </Routes>
    </div>
  );
}

export default App;
