import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ticket } from 'src/app/model/ticket';
import { TicketDto } from 'src/app/model/ticket-dto';
import { MovieService } from 'src/app/services/movie.service';
import { TicketService } from 'src/app/services/ticket.service';
import { UserAuthService } from 'src/app/services/user-auth.service';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent implements OnInit {

  ticket: TicketDto | undefined;

  tickets: Ticket[] = [];

  movieId: number | undefined;

  displayedColumns: string[] = ['Transaction ID', 'Movie Name', 'Theatre Name', 'Total Seats', 'Available Seats', 'Booked Seats'];

  constructor(private ticketService: TicketService, private movieService: MovieService, private userAuthService: UserAuthService, 
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.movieId = +params['movieId'];
      this.getTicketsByMovieId(this.movieId);
    })
  }

  public bookTicket(ticketDto: TicketDto) {
    this.ticketService.bookTicket(ticketDto).subscribe(
      (response: TicketDto) => {
        this.ticket = response;
        console.log(response);
      },
      (error) => {
        this.movieService.getAllMovies();
        console.log(error);
      }
    );
  }

  public getTicketsByUserIdAndMovieId(userId: number, movieId: number) {
    
  }

  public getTicketsByMovieId(movieId: number) {
    this.ticketService.getTicketsByMovieId(movieId).subscribe(
      (response: any) => {
        this.tickets = response;
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  public isAdmin() {
    return this.userAuthService.isAdmin();
  }


}
