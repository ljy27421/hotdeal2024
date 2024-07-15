import React from 'react';
import './Footer.css';

const Footer = () => {
  return (
    <footer>
      <div className="footerContainer">
        <div className="socialIcons">
          <a href="https://www.facebook.com"><i className="fa-brands fa-facebook"></i></a>
          <a href="https://www.instagram.com"><i className="fa-brands fa-instagram"></i></a>
          <a href="https://www.twitter.com"><i className="fa-brands fa-twitter"></i></a>
          <a href="https://plus.google.com"><i className="fa-brands fa-google-plus"></i></a>
          <a href="https://www.youtube.com"><i className="fa-brands fa-youtube"></i></a>
        </div>
        <div className="footerNav">
          <ul>
            <li><a href="/">test</a></li>
            <li><a href="/news">test</a></li>
            <li><a href="/about">test</a></li>
            <li><a href="/contact">test</a></li>
            <li><a href="/team">test</a></li>
          </ul>
        </div>
      </div>
      <div className="footerBottom">
        <p>test &copy;2024; test by <span className="designer">핫딜 커뮤</span></p>
      </div>
    </footer>
  );
};

export default Footer;
