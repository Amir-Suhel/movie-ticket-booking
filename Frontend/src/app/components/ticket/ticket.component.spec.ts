import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { Ticket } from 'src/app/model/ticket';
import { TicketService } from 'src/app/services/ticket.service';

import { TicketComponent } from './ticket.component';

fdescribe('TicketComponent', () => {
  let component: TicketComponent;
  let fixture: ComponentFixture<TicketComponent>;
  let ticketService: TicketService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TicketComponent ],
      imports: [HttpClientTestingModule],
      providers:[
        TicketService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({movieId: 1})
          }
        }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketComponent);
    component = fixture.componentInstance;
    ticketService = TestBed.inject(TicketService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getTicketsByMovieId method from ticket.comonent.ts', () => {
    const tickets: Ticket[] = [];
    spyOn(ticketService, 'getTicketsByMovieId').and.returnValue(of(tickets));
    component.ngOnInit();
    expect(ticketService.getTicketsByMovieId).toHaveBeenCalledWith(1);
  });


});
