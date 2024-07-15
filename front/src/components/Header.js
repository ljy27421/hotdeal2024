import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import './Header.css';
import logoImage from './Assets/logoimage.jpg';

const Header = () => {
  useEffect(() => {
    const toggleBtn = document.querySelector('.navbar_toggleBtn');
    const menu = document.querySelector('.navbar_menu');
    const icons = document.querySelector('.navbar_login_signup');

    const toggleMenu = () => {
      menu.classList.toggle('active');
      icons.classList.toggle('active');
    };

    toggleBtn.addEventListener('click', toggleMenu);

    // Clean up the event listener on component unmount
    return () => {
      toggleBtn.removeEventListener('click', toggleMenu);
    };
  }, []);

  return (
    <nav className="navbar">
      <div className="navbar_logo">
        <img src={logoImage} alt="logo" className="logoimage" />
        <Link to="/home">HotdealWork</Link>
      </div>

      <ul className="navbar_menu">
        <li><Link to="/info">이벤트/특가</Link></li>
        <li><Link to="/discussion">질문/토론</Link></li>
        <li><Link to="/community">커뮤니티</Link></li>
        <li><Link to="/market">핫딜/장터</Link></li>
      </ul>

      <div style={{ flexGrow: 0.9 }}></div>

      <ul className="navbar_login_signup">
        <li><Link to="/Login">로그인</Link></li>
        <li><Link to="/Signup">회원가입</Link></li>
      </ul>

      <div className="navbar_toggleBtn">
        <i className="fa-solid fa-bars"></i>
      </div>
    </nav>
  );
};

export default Header;
