import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatTableModule } from '@angular/material/table';
import { Movie } from 'src/app/model/movie';
import { MovieService } from 'src/app/services/movie.service';

import { ShowMovieComponent } from './show-movie.component';
import { of } from 'rxjs';
import Swal from 'sweetalert2';

describe('ShowMovieComponent', () => {
  let component: ShowMovieComponent;
  let fixture: ComponentFixture<ShowMovieComponent>;
  let movieService: MovieService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShowMovieComponent],
      imports: [
        HttpClientTestingModule,
        MatTableModule,
      ],
      providers: [MovieService]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowMovieComponent);
    component = fixture.componentInstance;
    movieService = TestBed.inject(MovieService);
    fixture.detectChanges();
  });
    
  
  it('should create', () => {
    expect(component).toBeTruthy();
  });

 

  // it('should call getAllMovies method ', () => {
  //   const movies: Movie[] = [
  //     { movieId: 1, movieName: 'Movie A', theatreName: 'Theatre A', totalSeats: 100 },
  //     { movieId: 2, movieName: 'Movie B', theatreName: 'Theatre B', totalSeats: 120 },
  //    { movieId: 3, movieName: 'Movie C', theatreName: 'Theatre C', totalSeats: 130 }
  //   ];
  //   spyOn(movieService, 'getAllMovies').and.returnValue(of(movies));
  //   component.ngOnInit();
  //   expect(component.movies).toEqual(movies);
  //   expect(movieService.getAllMovies()).toHaveBeenCalled();
  // });

});
