import React from 'react';
import './css/home.component.css';

const Home = () => {
  return (
    <div className="app-info">
      <img src="/clancy4.jpg" alt="App info" className="app-info-image" />
      <div className="app-info-text">
        <h1>Добро пожаловать в <strong>Soundtracker</strong></h1>
        <p>Soundtracker - это приложение, которое взаимодействует с Spotify API и Kinopoisk API с целью получения данных об
          кино и музыке.</p>
      </div>
    </div>
  );
};

export default Home;