import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Ticket } from '../model/ticket';
import { TicketDto } from '../model/ticket-dto';

import { TicketService } from './ticket.service';

fdescribe('TicketService', () => {
  let service: TicketService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers:[TicketService]
    });
    service = TestBed.inject(TicketService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should bookTicket() of ticket.service.ts', () => {
    const ticketDto: TicketDto = {
      transactionId: 1, movieName: "ABC", theatreName: "XYZ", noOfSeats: 5
    };

    service.bookTicket(ticketDto).subscribe(response => {
      expect(response).toBeTruthy();
    });

    const request = httpMock.expectOne('http://localhost:9090/api/v1/moviebooking/ticket/book');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(ticketDto);
    request.flush({});

  });

  it('should getAllTicketsByMovieId(movieId) of ticket.service.ts', () => {
    const movie = {
      movieId: 2,
      movieName: "Movie A",
      theatreName: "Theatre A",
      totalSeats: 100
    }
    const id = 2;
    const dummyTickets: Ticket[] = [
      {transactionId: 1, movieName: "Movie A", theatreName: "Theatre A", totalSeats: 100,availableSeats: 90,bookedSeats: 10 },
      {transactionId: 2, movieName: "Movie A", theatreName: "Theatre A", totalSeats: 100,availableSeats: 80,bookedSeats: 10 },
      {transactionId: 3, movieName: "Movie A", theatreName: "Theatre A", totalSeats: 100,availableSeats: 70,bookedSeats: 10 }
    ];

    service.getTicketsByMovieId(id).subscribe(tickets => {
      //expect(tickets.).toBe(dummyTickets.length);
      expect(tickets).toEqual(dummyTickets);

    });

    const request = httpMock.expectOne('http://localhost:9090/api/v1/moviebooking/ticket' + '/movie/' + id);
    expect(request.request.method).toBe('GET');
    request.flush(dummyTickets);

  });

});


