import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import MovieService from '../services/movie.service';
import './css/movie-list.component.css';

const MovieList = () => {
    const [movies, setMovies] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        retrieveMovies();
    }, []);

    const retrieveMovies = () => {
        MovieService.getAllMoviesDto()
            .then(response => {
                setMovies(response.data);
                console.log(response.data);
                setIsLoading(false);
            })
            .catch(e => {
                console.error(e);
                setIsLoading(false);
            });
    };

    if (isLoading) {
        return <div>Loading...</div>;
    }
    const handleSearchTermChange = event => {
        setSearchTerm(event.target.value);
    };

    const filteredMovies = movies.filter(movie =>
        movie.ruTitle.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="container mt-3">
            <input
                value={searchTerm}
                onChange={handleSearchTermChange}
                placeholder="Поиск по названию фильма"
                className="form-control mb-3"
            />
            <div className="grid">
                {filteredMovies.map((movie, index) => (
                    <div className="card" key={index}>
                        <Link to={`/movies/${movie.id}`}>
                            <img className="card-img-top" src={movie.poster} alt="Movie poster"/>
                            <div className="card-body">
                                <h5 className="card-title">{movie.ruTitle}</h5>
                                <p className="card-text">
                                    <small className="text-muted">{movie.releaseYear}</small>
                                </p>
                                <p className="card-text">{movie.length} минут</p>
                                <p className="card-text">Жанры: {movie.genres.join(', ')}</p>
                            </div>
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default MovieList;