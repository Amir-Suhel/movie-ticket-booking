import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestdemoComponent } from './testdemo.component';

fdescribe('TestdemoComponent', () => {
  let component: TestdemoComponent;
  let fixture: ComponentFixture<TestdemoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestdemoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestdemoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return sum', () => {
    const val = component.sumOfNumbers(3, 5);
    expect(val).toEqual(8);
  });

  it('should return even', () => {
    const val = component.evenOrOdd(8)
    expect(val).toEqual('EVEN');
  });

  it('should return odd', () => {
    const val = component.evenOrOdd(7)
    expect(val).toEqual('ODD');
  });

});
