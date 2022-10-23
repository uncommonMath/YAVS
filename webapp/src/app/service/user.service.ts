import { Injectable } from '@angular/core'
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { AuthUser } from '../model/auth-user'
import { NewUser } from '../model/new-user'
import { User } from '../model/user'
//import { ApprovedUser } from '@app/model/approved-user'
import { Observable } from 'rxjs'

//import { CookieService  } from 'ngx-cookie-service'

//import { AlterPasswordUser } from '@app/model/alter-password-user'


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersUrl: string

  constructor(private http: HttpClient/*, private cookieService: CookieService*/) {
    this.usersUrl = 'http://localhost:8080/user/'
  }

  public registerUser(newUser: NewUser) {
    return this.http.post<NewUser>(this.usersUrl + 'register', newUser)
  }

  /*public loginUser(authUser: AuthUser) {
   // let approvedUser = this.http.post<ApprovedUser>(this.usersUrl + 'login', authUser)
    approvedUser.subscribe(result => {
      if (result) {
        /*this.cookieService.set('access', result.accessToken)
        this.cookieService.set('id', String(result.user.userId))
      }
    })
    return approvedUser
  }*/

  public getUserByUserMail(userMail: string) {
    const headers = new HttpHeaders()
    const params = new HttpParams().append('userMail', userMail)
    return this.http.get<User>(this.usersUrl + 'getByUserMail', { headers, params })
  }

  public getUserByUserId(userId: number) {
    const headers = new HttpHeaders()
    const params = new HttpParams().append('userId', userId)
    return this.http.get<User>(this.usersUrl + 'getByUserId', { headers, params })
  }

  public getUserNamesByUserIds(userIds: number[]) {
    const headers = new HttpHeaders()
    //const params = new HttpParams().append('userIds', userIds)
    let params = new HttpParams()
    userIds.forEach((x: number) => {
      params = params.append('userIds', x)
    })
    return this.http.get<User[]>(this.usersUrl + 'getUsersByUserIds', { headers, params })
  }

  public update(user: User) {
    return this.http.put<User>(this.usersUrl + 'update', user)
  }

  /*public changePassword(user: AlterPasswordUser) {
    return this.http.put<User>(this.usersUrl + 'changePassword', user)
  }*/
}
