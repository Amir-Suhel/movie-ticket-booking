import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { Movie } from 'src/app/model/movie';
import { MovieService } from 'src/app/services/movie.service';

import { AddMovieComponent } from './add-movie.component';

fdescribe('AddMovieComponent', () => {
  let component: AddMovieComponent;
  let fixture: ComponentFixture<AddMovieComponent>;
  let movieService: MovieService;
  let movie: Movie;
  let httpClient: HttpClient;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddMovieComponent ],
      imports:[
        HttpClientTestingModule,
        FormsModule
      ]
    })
    .compileComponents();
    httpClient = TestBed.inject(HttpClient);
    movieService = TestBed.inject(MovieService);

    fixture = TestBed.createComponent(AddMovieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return http post call of add-movie-component', () => {
    movie = {
      movieId: 5,
      movieName: "The Godfather",
      theatreName: "ABC Cinema",
      totalSeats: 100
    }

    movieService.addMovie(movie).subscribe({
      next:(data) => {
        expect(data).toBeTruthy();
        expect(data.movieId).toEqual(5);
      }
    });

    const httpCtrl = TestBed.inject(HttpTestingController);
    const mockHttp = httpCtrl.expectOne('http://localhost:9090/api/v1/moviebooking/addmovie');
    const httpReq = mockHttp.request;

    expect(httpReq.method).toEqual('POST');
  });

});
