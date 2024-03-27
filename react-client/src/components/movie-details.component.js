import React, {useState, useEffect, useCallback} from 'react';
import {useParams} from 'react-router-dom';
import MovieService from '../services/movie.service';
import './css/movie-details.component.css';
import ScreenshotDialog from "./screenshot-dialog.component";

const MovieDetails = () => {
    const [movie, setMovie] = useState(null);
    const [hoveredDirector, setHoveredDirector] = useState(null);
    const [hoveredActor, setHoveredActor] = useState(null);
    const [selectedScreenshot, setSelectedScreenshot] = useState(null);
    const {id} = useParams();

    const retrieveMovie = useCallback(() => {
        MovieService.getMovieById(id)
            .then(response => {
                setMovie(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.error(e);
            });
    }, [id]);

    useEffect(() => {
        retrieveMovie();
    }, [retrieveMovie]);

    if (!movie) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container mt-3">
            <div className="row">
                <div className="col-md-6">
                    <img className="img-fluid" src={movie.poster} alt="Movie poster"/>
                </div>
                <div className="col-md-6">
                    <h2>{movie.ruTitle}</h2>
                    <p>{movie.description}</p>
                    <p><strong>Год производства:</strong> {movie.releaseYear}</p>
                    <p><strong>Продолжительность:</strong> {movie.length} минут</p>
                    <p><strong>Жанры:</strong> {movie.genres.map(genre => genre.name).join(', ')}</p>
                    <p><strong>Режиссеры:</strong> {movie.directors.map((director, index) => (
                        <span
                            key={index}
                            onMouseEnter={() => setHoveredDirector(director)}
                            onMouseLeave={() => setHoveredDirector(null)}
                            className="director"
                        >
              {director.ruName}
                            {hoveredDirector === director &&
                                <img className="hover-image" src={director.photo} alt="Director"/>}
            </span>
                    )).reduce((prev, curr, index) => [prev, ', ', curr])}</p>
                    <p><strong>Актеры:</strong> {movie.actors.map((actor, index) => (
                        <span
                            key={index}
                            onMouseEnter={() => setHoveredActor(actor)}
                            onMouseLeave={() => setHoveredActor(null)}
                            className="actor"
                        >
              {actor.ruName}
                            {hoveredActor === actor && <img className="hover-image" src={actor.photo} alt="Actor"/>}
            </span>
                    )).reduce((prev, curr, index) => [prev, ', ', curr])}</p>
                    <div className="screenshot-container">
                        {movie.movieScreenshots.map((screenshot, index) => (
                            <img key={index} src={screenshot.url} alt="Movie screenshot"
                                 onClick={() => setSelectedScreenshot(screenshot.url)}/>
                        ))}
                    </div>
                </div>
            </div>
            {movie.album && (
                <div className="album-container">
                    <img src={movie.album.cover} alt="Album cover" className="w-48 h-auto"/>
                    <div className="ml-8">
                        <h2 className="text-2xl font-bold">{movie.album.name}</h2>
                        <a href={movie.album.spotifyUrl} target="_blank" rel="noreferrer" className="spotify-button">Open
                            in Spotify</a>
                    </div>
                </div>
            )}
            {movie.album && movie.album.tracks.map((track, index) => (
                <div key={index} className="track-container">
                    <p>{track.name}</p>
                    <audio controls style={{width: '500px'}}>
                        <source src={track.previewUrl} type="audio/mpeg"/>
                        Your browser does not support the audio element.
                    </audio>
                </div>
            ))}
            <ScreenshotDialog isOpen={!!selectedScreenshot} onRequestClose={() => setSelectedScreenshot(null)}
                              screenshotUrl={selectedScreenshot}/>
        </div>
    );
};

export default MovieDetails;