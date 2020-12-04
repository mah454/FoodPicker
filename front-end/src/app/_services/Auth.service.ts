import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { environment } from "./ApiEnvironment";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private httpClient: HttpClient, private router: Router) {}

  // Local Storage
  public storeToken(token: string) {
    localStorage.setItem("token", token);
  }

  public removeToken() {
    localStorage.removeItem("token");
  }

  public getToken() {
    return localStorage.getItem("token");
  }

  // Authentication
  public login() {
    return this.httpClient.get(environment.BASE_URL + environment.LOGIN_PATH);
  }

  public verifyToken() {
    return this.httpClient.get(
      environment.BASE_URL + environment.VERIFY_TOKEN_PATH,
      {
        observe: "response",
      }
    );
  }

  public logout() {
    this.httpClient
      .get(environment.BASE_URL + environment.LOGOUT_PATH)
      .toPromise()
      .then(() => this.removeToken())
      .then(() => this.router.navigate(["/"]));
  }
}
