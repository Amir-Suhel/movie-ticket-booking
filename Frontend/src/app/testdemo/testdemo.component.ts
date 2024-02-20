import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-testdemo',
  templateUrl: './testdemo.component.html',
  styleUrls: ['./testdemo.component.css']
})
export class TestdemoComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  sumOfNumbers(num1: number, num2: number): number {
    return num1 + num2;
  }

  evenOrOdd(val: number): string {
    return val % 2 == 0 ? "EVEN" : "ODD";
  }

}
