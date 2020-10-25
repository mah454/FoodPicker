import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { loginPath, logoutPath, verifyTokenPath } from "./food-picker-env";

interface LoginResponse {
  url: String;
}

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient, private route: Router) {}

  public login() {
    this.http.get(loginPath).subscribe(
      (response: LoginResponse) =>
        (window.location.href = response.url.toString())
      // (err) => console.log(">>> :" + err),
      // () => console.log("COMPLETED")
    );
  }

  public logout() {
    this.http
      .get(logoutPath)
      .toPromise()
      .then(() => this.removeToken())
      .then(() => this.route.navigate(["/login"]));
  }

  public storeToken(token: string) {
    localStorage.setItem("token", token);
  }

  public getToken() {
    return localStorage.getItem("token");
  }

  public removeToken() {
    localStorage.removeItem("token");
  }

  public validateToken() {
    this.http.get(verifyTokenPath, { observe: "response" }).subscribe(
      (response) => this.route.navigate(["panel"]),
      (err) => {
        this.removeToken();
        this.route.navigate(["/login"]);
      }
    );
  }

  public isLoggedIn() {
    return this.getToken();
  }
}
