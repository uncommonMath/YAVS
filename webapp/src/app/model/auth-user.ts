export class AuthUser {
	userMail: string;
	password: string;
	
	constructor(userMail: string, password: string){
		this.userMail = userMail;
		this.password = password;
	}
}
