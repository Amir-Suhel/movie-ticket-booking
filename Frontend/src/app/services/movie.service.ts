import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Movie } from '../model/movie';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  baseUrl = 'http://localhost:9090/api/v1/moviebooking';

  constructor(private httpClient: HttpClient) { }

  //getAllMovies() api
  public getAllMovies() {
    return this.httpClient.get(this.baseUrl + '/all');
  }

  //addMovie() api
  public addMovie(movie: Movie) {
    return this.httpClient.post<Movie>(this.baseUrl + '/addmovie', movie);
  }

  //getMovieById()
  // public getMovieById(movieId: number) {
  //   return this.httpClient.get(this.baseUrl + '/movie/' + movieId);
  // }

  //deleteMovie
  public deleteMovie(movieId: number) {
    return this.httpClient.delete(this.baseUrl + '/delete/' + movieId);
  }

}
