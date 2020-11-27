import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class ApiService {
  private BASE_URL = "http://localhost:8080/api/v1";
  private LOGON_PATH = this.BASE_URL + "/auth/login";
  private LOGOUT_PATH = this.BASE_URL + "/auth/login";
  private VERIFY_TOKEN_PATH = this.BASE_URL + "/auth/verify";

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
    return this.httpClient.get(this.LOGON_PATH);
  }

  public verifyToken() {
    return this.httpClient.get(this.VERIFY_TOKEN_PATH, {
      observe: "response",
    });
  }

  public logout() {
    this.httpClient
      .get(this.LOGOUT_PATH)
      .toPromise()
      .then(() => this.removeToken())
      .then(() => this.router.navigate(["/"]));
  }
}
