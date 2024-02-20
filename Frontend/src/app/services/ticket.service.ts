import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TicketDto } from '../model/ticket-dto';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  baseUrl: string = 'http://localhost:9090/api/v1/moviebooking/ticket'

  constructor(private httpClient: HttpClient) { }

  public bookTicket(ticketDto: TicketDto) {
    return this.httpClient.post<any>(this.baseUrl + '/book', ticketDto);
  }

  public getTicketsByMovieId(movieId: number) {
    return this.httpClient.get(this.baseUrl + '/movie/' + movieId)
  }

}
