import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Movie } from 'src/app/model/movie';
import { TicketDto } from 'src/app/model/ticket-dto';
import { MovieService } from 'src/app/services/movie.service';
import { TicketService } from 'src/app/services/ticket.service';
import { UserAuthService } from 'src/app/services/user-auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-show-movie',
  templateUrl: './show-movie.component.html',
  styleUrls: ['./show-movie.component.css']
})
export class ShowMovieComponent implements OnInit {

  movies: Movie[] = [];
  ticketDto: TicketDto = new TicketDto();
  displayedColumnsAdmin: string[] = ['Movie ID', 'Movie Name', 'Theatre Name', 'Total Seats', 'Show Tickets', 'Book Ticket', 'Delete Movie'];
  displayedColumnsUser: string[] = ['Movie ID', 'Movie Name', 'Theatre Name', 'Total Seats', 'Book Ticket'];

  constructor(public movieService: MovieService, private ticketService: TicketService, private router: Router,
    private userAuthService: UserAuthService) { }

  ngOnInit(): void {
    this.getAllMovies();
  }

  public getAllMovies() {
    this.movieService.getAllMovies().subscribe(
      (response: any) => {
        console.log(response);
        this.movies = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  public deleteMovie(movieId: number) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
      this.movieService.deleteMovie(movieId).subscribe(
      () => {
        Swal.fire(
          'Deleted!',
          'Your file has been deleted.',
          'success'
        )
        this.getAllMovies();
      },
      (error) => {
        console.log(error);
        Swal.fire(
          'Error',
          'Something wrong!!',
          'error'
        )
      }
    );
      }
    })
  }

  public bookTicket(element: any) {
    this.ticketDto.movieName = element.movieName;
    this.ticketDto.theatreName = element.theatreName;
    let seats: any = prompt("Enter number of seats");
    let bookedSeats: number = +seats;

    if (bookedSeats != 0 && bookedSeats != null) {
      this.ticketDto.noOfSeats = bookedSeats;
      this.bookTicketApi(this.ticketDto);
    }

    else {
      alert("Number of seats can't be null or zero");
    }

  }

  public bookTicketApi(ticketDto: TicketDto) {
    this.ticketService.bookTicket(ticketDto).subscribe(
      (response: any) => {
        console.log(response);
        if(response)
        alert("Congratulations, Ticket has been booked!!")
      },
      (error) => {
        console.log(error);
        console.log(error.status);
        alert("All seats are already booked!!")
      }
    );
  }

  public showTickets(movieId: number) {
    this.router.navigate(['/ticket/' + movieId]);
  }

  public isAdmin() {
    return this.userAuthService.isAdmin();
  }

}
