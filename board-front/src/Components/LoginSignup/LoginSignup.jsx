import React, { useState } from 'react'
import './LoginSignup.css'

import uesr_icon from '../Assets/person.png'
import email_icon from '../Assets/email.png'
import password_icon from '../Assets/password.png'


const LoginSignup = () => {

  const [action, setAction] = useState("회원가입");


  return (
    <div className='container'>
      <div className="header">
        <div className="text">{action}</div>
        <div className="underline"></div>
      </div>
      <div className="inputs">
        {action==="로그인"?<div></div>:<div className="input">
          <img src={uesr_icon} alt="" />
          <input type="text" placeholder='이름'/>
        </div>}

        <div className="input">
          <img src={email_icon} alt="" />
          <input type="email" placeholder='이메일'/>
        </div>
        <div className="input">
          <img src={password_icon} alt="" />
          <input type="password" placeholder='비밀번호'/>
        </div>
      </div>
      {action==="회원가입"?<div></div>:<div className="forgot-password">암호를 잃어버렸습니다. <span>여기를 클릭하세요!</span></div>}
      <div className="submit-container">
        <div className={action==="로그인"?"submit gray":"submit"} onClick={()=>{setAction("회원가입")}}>회원가입</div>
        <div className={action==="회원가입"?"submit gray":"submit"}onClick={()=>{setAction("로그인")}}>로그인</div>
      </div>
    </div>
  );
};

export default LoginSignup;
