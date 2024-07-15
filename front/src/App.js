import React from 'react';
import './App.css';
import Header from './components/Header';
import Footer from './components/Footer';
import Login from './components/LoginSignup/Login';
import Signup from './components/LoginSignup/Signup'; // Aliased to Signup
import Info from './components/LoginSignup/Info';
import Home from './components/LoginSignup/Home';


import { BrowserRouter, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path='/HOME' element={<Home />} />
          <Route path='/info' element={<Info />} />
          <Route path='/login' element={<Login />} />
          <Route path='/Signup' element={<Signup />} />
        </Routes>
      </BrowserRouter>
      <Footer />
    </div>
  );
}

export default App;
