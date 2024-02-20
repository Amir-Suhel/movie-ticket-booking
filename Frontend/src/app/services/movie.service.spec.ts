import { TestBed } from '@angular/core/testing';
import { Movie } from '../model/movie';
import { MovieService } from './movie.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { of } from 'rxjs';
import { ShowMovieComponent } from '../components/show-movie/show-movie.component';

fdescribe('MovieService', () => {
  let movieService: MovieService;
  let movieComponent: ShowMovieComponent;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShowMovieComponent],
      imports:[
        HttpClientTestingModule,
        HttpClientModule
      ],
      providers: [
        MovieService, ShowMovieComponent
      ]
    });
    movieService = TestBed.inject(MovieService);
    movieComponent = TestBed.inject(ShowMovieComponent);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(movieService).toBeTruthy();
  });

  it('should call getAllMovies() from movie.service', () => {
    let response: Movie[] | any;
    let spy = spyOn(movieService, 'getAllMovies').and.returnValue(of(response));
    expect(movieComponent.getAllMovies()).toEqual(response);
  });

  // it('should call delete() from movie service', () => {
  //   const movieId = 1;
  //   let spy = spyOn(movieService, 'deleteMovie').and.returnValue(of())
  //   movieComponent.deleteMovie(movieId);
  //   expect(spy).toHaveBeenCalled();
  // });


  it('should addMovie() from movie.service', () => {
    const movie: Movie = {
      movieId: 1,
      movieName: "Movie A",
      theatreName: "Theatre A",
      totalSeats: 100
    };

    movieService.addMovie(movie).subscribe(response => {
      expect(response).toEqual(movie);
    });

    const request = httpMock.expectOne('http://localhost:9090/api/v1/moviebooking/addmovie');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(movie);
    request.flush(movie);

  });

  it('should deleteMovie from movie.service', () => {

    const movieId = 1;
    movieService.deleteMovie(movieId).subscribe(response => {
      expect(response).toBeNull();
    });

    const request = httpMock.expectOne('http://localhost:9090/api/v1/moviebooking'+ '/delete/' + movieId);
    expect(request.request.method).toBe('DELETE');
    request.flush(null);
  });

  
});
