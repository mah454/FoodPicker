import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ApiService } from "../_services/api.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  constructor(private api: ApiService, private router: Router) {
    let queryParams = this.getParameters();
    let token = api.getToken();
    if (token) {
      api.verifyToken().subscribe(
        (resp) => router.navigate(["panel"]),
        (err) => {
          router.navigate(["login"]);
          api.removeToken();
        }
      );
    } else {
      token = queryParams["token"];
      if (token) {
        api.storeToken(token);
        api.verifyToken().subscribe(
          (res) => router.navigate(["panel"]),
          (err) => console.log("Error : " + err)
        );
      }
    }
  }

  ngOnInit(): void {}

  login() {
    this.api
      .login()
      .subscribe((e) => (window.location.href = Object(e)["url"]));
  }

  private getParameters() {
    let queryParams = location.search;
    let value = queryParams.substr(1).split("=")[1];
    return { token: value };
  }
}
