import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { loginPath, verifyTokenPath } from "./food-picker-env";

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

  public storeToken(token: string) {
    localStorage.setItem("token", token);
  }

  public getToken() {
    return localStorage.getItem("token");
  }

  public removeToken() {
    localStorage.removeItem("token");
  }

  public validateToken(token: string) {
    let reqParam = new HttpParams().set("token", token);

    this.http
      .get(verifyTokenPath, { observe: "response", params: reqParam })
      .toPromise()
      .then(
        (response) => {
          this.storeToken(token);
          this.route.navigate(["panel"]);
        },
        (err) => {
          this.removeToken();
          this.route.navigate(["login"]);
        }
      );
  }

  public isLoggedIn() {
    return this.getToken();
  }
}
