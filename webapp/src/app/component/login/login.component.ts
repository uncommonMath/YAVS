import { ChangeDetectorRef, Component, OnInit } from '@angular/core'

import {FormControl, FormGroup, Validators, ValidatorFn, AbstractControl, ValidationErrors} from '@angular/forms'
import {TuiValidationError} from '@taiga-ui/cdk'

//import { User } from '@app/model/user'
import { AuthUser } from '../../model/auth-user'
//import { UserService } from '@app/service/user.service'
//import { CookieService  } from 'ngx-cookie-service'

import {ChangeDetectionStrategy, ViewEncapsulation} from '@angular/core';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less'],
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent implements OnInit {
  authUser: AuthUser
  error: string | null
  
  constructor(/*private cs: CookieService, private router: Router, private userService: UserService, private cd: ChangeDetectorRef*/) {
    this.authUser = new AuthUser('', '')
	this.error = null
  }

  ngOnInit(): void {
  }
  
  onSubmit() {
	this.setError(null)
    //this.userService.loginUser(this.authUser).subscribe(result => this.onLogin(result), error => this.setError('Something went wrong...'))
  }

  onLogin(result: any) {
	/*console.log(result)
	if (result == null)
	{
		this.setError('Invalid login or password')
	}
	else
	{
		this.setError(null)
		this.cs.set('requreReload', 'true')
		this.router.navigate(['/home'])
	}*/
  }
  
  setError(error: string | null) {
	this.error = error
	//this.cd.detectChanges()
  }
  
  checkPasswords: ValidatorFn = (field: AbstractControl):  ValidationErrors | null => { 
    let pass = field.root.get('passwordControl')?.value
	if (pass?.length < 6 || pass?.length > 18) {
		return {
			lengthError: 'Password\'s length should be between 6 and 18'
		}
	}
	return null
  }
  
  readonly loginForm = new FormGroup({
    emailControl: new FormControl('', [
		Validators.required,
		Validators.email
	]),
    passwordControl: new FormControl('', [
		this.checkPasswords
	]),
  })
}
