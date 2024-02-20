import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserAuthService } from 'src/app/services/user-auth.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

export interface ResetPassword {
  email: string;
  secretQuestion: string;
  newPassword: string;
}

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  public resetPassword: ResetPassword = {
    email: '',
    secretQuestion: '',
    newPassword: ''
  }

  constructor(private userService: UserService, private userAuthService: UserAuthService,
    private router: Router, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  public forgotPassword() {
    this.userService.forgotPassword(this.resetPassword).subscribe(
      (response: any) => {
        // if (response.status === 201) {
        //   alert("Password reset successfully!!");
        //   this.router.navigate(['/']);
        // }

        // else if(response.status === 400) {
        //   alert("Secret Question or email are wrong!!");
        // }
        // alert("Password reset succesfully!!")
        console.log(response);
        Swal.fire('Success', 'Password reset sucessfully!!', 'success');
        this.router.navigate(['/']);


      },
      (error) => {
        console.log(error);
        //alert("Email or secret question are wrong!!");
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Email or secret question are wrong!!',
        })
      }
    );
  }

  // public forgotPassword() {
  //   this.userService.forgotPassword(this.resetPassword).subscribe(
  //     (response: any) => {
  //       // if(response.)
  //       // if(response.status === 201)
  //       this.snackBar.open('Password reset successfully!!', 'OK', { duration: 3000 });
  //       console.log(response);
  //     },
  //     (error) => {
  //       this.snackBar.open('Secret Question or email are wrong!!', 'OK', { duration: 3000 });
  //     }
  //   );

  // }

}
