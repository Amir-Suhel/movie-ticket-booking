import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from 'src/app/services/user-auth.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private userService: UserService, private userAuthService: UserAuthService,
    private router: Router) { }

  ngOnInit(): void {
  }


  public signup(signupForm: NgForm) {
    this.userService.signup(signupForm.value).subscribe(
      (response: any) => {
        //alert("Wecome, You have registerd successfully!!")
        // console.log(response);
        // this.userAuthService.setRoles(response.roles);
        //const role = response.roles[0];
        // if (role == 'ROLE_ADMIN') {
        //   this.router.navigate(['/admin']);
        // } else {
        //   this.router.navigate(['/user']);
        // }
        Swal.fire('Success', 'User is registered', 'success');
        this.router.navigate(['/']);
      },
      (error) => {
        console.log(error);
        if(error.status === 409)
        alert("Email is already taken!!");
        else
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Something went wrong or email alreay taken',
        })
      }
    );
  }

}
