import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add login processing logic here.
    console.log('ID:', id);
    console.log('Password:', password);
  };

  return (
    <div className="box">
      <form onSubmit={handleSubmit}>
        <h2>로그인</h2>
        <div className="inputBox">
          <input 
            type="text" 
            required 
            value={id} 
            onChange={(e) => setId(e.target.value)}
          />
          <span>아이디</span>
          <i></i>
        </div>
        <div className="inputBox">
          <input 
            type="password" 
            required 
            value={password} 
            onChange={(e) => setPassword(e.target.value)}
          />
          <span>비밀번호</span>
          <i></i>
        </div>
        <div className="links">
          <Link to="/signup">회원가입</Link>
          <Link to="/forgot-password">비밀번호 찾기</Link>
        </div>
        <input type="submit" value="로그인" />
      </form>
    </div>
  );
};

export default Login;
