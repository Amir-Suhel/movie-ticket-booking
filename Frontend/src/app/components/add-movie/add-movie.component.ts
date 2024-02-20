import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Movie } from 'src/app/model/movie';
import { MovieService } from 'src/app/services/movie.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css']
})
export class AddMovieComponent implements OnInit {

  movie: Movie = new Movie();
  constructor(private movieServie: MovieService, private router: Router) { }

  ngOnInit(): void {
  }

   public addMovie(movieForm: NgForm) {
    this.movieServie.addMovie(this.movie).subscribe(
      (response: any) => {
        console.log(response);
        Swal.fire('Success', 'Movie added!!', 'success');
        this.router.navigate(['/']);
      },
      (error) => {
        console.log(error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Movie is already present with id: ' + this.movie.movieId,
        })
      }
    );
   }

}
