import React, { useState, useEffect } from 'react';
import './Home.css';

const Home = () => {
  const [date, setDate] = useState('');
  const [day, setDay] = useState('');
  const [month, setMonth] = useState('');
  const [year, setYear] = useState('');

  useEffect(() => {
    const today = new Date();
    const dayOfWeek = today.getDay();
    const monthIndex = today.getMonth();

    // 요일과 월을 문자열로 가져오기 위해 배열 정의
    const weekDays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    // 상태 업데이트
    setDate(today.getDate());
    setDay(weekDays[dayOfWeek]);
    setMonth(months[monthIndex]);
    setYear(today.getFullYear());
  }, []);

  return (
    
    <div className="home-page page">
      <div className="hero">
        <div className="cont-box"> 
          <div className="calendar"> 
            <div className="left">
              <p id="date">{date}</p>
              <p id="day">{day}</p>
            </div>
            <div className="right">
              <p id="month">{month}</p>
              <p id="year">{year}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
