import { Component, OnInit, Inject } from '@angular/core'
import {FormControl, FormGroup, Validators, ValidatorFn, AbstractControl, ValidationErrors} from '@angular/forms'

import { Router } from '@angular/router'

import { ChangeDetectorRef } from '@angular/core'

import {TuiDialogContext, TuiDialogService} from '@taiga-ui/core'
import {PolymorpheusContent} from '@tinkoff/ng-polymorpheus'

import { User } from '../../model/user'
import { NewUser } from '../../model/new-user'
import { UserService } from '../../service/user.service'

const latinChars = /^[a-zA-Z0-9 ]+$/


import {ChangeDetectionStrategy, ViewEncapsulation} from '@angular/core';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.less'],
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegisterComponent implements OnInit {
  newUser: NewUser
  error: string | null

  constructor(private router: Router, private userService: UserService/*, private cd: ChangeDetectorRef,
	@Inject(TuiDialogService) private readonly dialogService: TuiDialogService,*/
  ) {
    this.newUser = new NewUser('', '', '')
	this.error = null
  }
 
  
  ngOnInit(): void {
  }

  onSubmit() {
	this.setError(null)
    this.userService.registerUser(this.newUser).subscribe(result => this.onRegister(result), error => this.setError('Something went wrong...'))
  }

  onRegister(result: any) {
	console.log(result)
	if (result == null)
	{
		this.setError('Something went wrong...')
	}
	else
	{
		this.router.navigate(['/login'])
	}
  }
  
  setError(error: string | null) {
	this.error = error
	//this.cd.detectChanges()
  }
  
  /*checkMailUnique: ValidatorFn = (field: AbstractControl):  ValidationErrors | null => {
	let mail = field.root.get('emailControl')?.value
	this.userService.getUserByUserMail(mail).subscribe(result => {
		console.log(result)
		if (result) {
			field.setErrors({
				mailError: 'This mail is already registered'
			})
		}
	})
	return null
  }*/
  
  checkPasswords: ValidatorFn = (field: AbstractControl):  ValidationErrors | null => { 
	if (!this.checkPasswordLength(field)) {
		return {
			lengthError: 'Password\'s length should be between 6 and 18'
		}
	}
	this.checkPasswordConfirmation(field)
	return null
  }
  
  checkPasswordsConfirm: ValidatorFn = (field: AbstractControl):  ValidationErrors | null => { 
	if (!this.checkPasswordLength(field)) {
		return {
			lengthError: 'Password\'s length should be between 6 and 18'
		}
	}
	if (!this.checkPasswordConfirmation(field)) {
		return {
			confirmError: 'Passwords should be same!'
		}
	}
	return null
  }
  
  checkPasswordLength(field: AbstractControl) : boolean {
	let value = field.value
	return value?.length > 5 && value?.length < 19
  }
  
  checkPasswordConfirmation(field: AbstractControl) : boolean {
	let passControl = field.root.get('passwordControl')
	let confirmPassControl = field.root.get('passwordConfirmControl')
	let pass = passControl?.value
    let confirmPass = confirmPassControl?.value
	if (passControl !== null && confirmPassControl !== null) {
	    let err = pass !== confirmPass ? {
		    confirmError: 'Passwords should be same'
	    } : null
		confirmPassControl.setErrors(err)
		return err === null
    }
	return false
  }
  
  checkUsername: ValidatorFn = (field: AbstractControl):  ValidationErrors | null => {
      let username = field.root.get('usernameControl')?.value
	  return latinChars.test(username) ? null : {
		  latinError: 'Username should contains latin letters only'
	  }
  }
  
  readonly registerForm = new FormGroup({
    emailControl: new FormControl('', {validators: [
		Validators.required,
		Validators.email,
		//this.checkMailUnique
	], updateOn: 'blur'}),
    usernameControl: new FormControl('', [
		this.checkUsername
	]),
    passwordControl: new FormControl('', [
		this.checkPasswords
	]),
    passwordConfirmControl: new FormControl('', [
		this.checkPasswordsConfirm
	])
  })
}
