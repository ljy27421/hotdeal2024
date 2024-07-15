import React, { useState } from 'react';
import './Login.css'; // Ensure you create and style this CSS file accordingly

const Signup = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [email, setEmail] = useState('');
  const [errors, setErrors] = useState({});

  const handleSubmit = (e) => {
    e.preventDefault();
    let validationErrors = {};

    if (password !== confirmPassword) {
      validationErrors.passwordMatch = 'Passwords do not match';
    }

    if (!validateEmail(email)) {
      validationErrors.email = 'Invalid email address';
    }

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    // Proceed with form submission (e.g., send data to backend)
    console.log({ username, password, email });
    alert('Signup successful!');
  };

  const validateEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
  };

  return (
    <div className="box">
      <form onSubmit={handleSubmit}>
        <h2>회원가입</h2>
        <div className="inputBox">
          <input 
            type="text" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            required 
          />
          <span>아이디</span>
          <i></i>
        </div>
        <div className="inputBox">
          <input 
            type="password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            required 
          />
          <span>비밀번호</span>
          <i></i>
        </div>
        <div className="inputBox">
          <input 
            type="password" 
            value={confirmPassword} 
            onChange={(e) => setConfirmPassword(e.target.value)} 
            required 
          />
          <span>비밀번호 재확인</span>
          <i></i>
        </div>
        <div className="inputBox">
          <input 
            type="email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            required 
          />
          <span>이메일</span>
          <i></i>
        </div>
        {errors.passwordMatch && <p className="error">{errors.passwordMatch}</p>}
        {errors.email && <p className="error">{errors.email}</p>}
        <input type="submit" value="회원가입" />
      </form>
    </div>
  );
};

export default Signup;
