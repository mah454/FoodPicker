import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ApiService } from "../_services/api.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  showSpinner = true;
  constructor(private api: ApiService, private router: Router) {
    let queryParams = this.getParameters();
    let token = api.getToken();
    if (token) {
      api.verifyToken().subscribe(
        (resp) => {
          router.navigate(["panel"]);
          this.showSpinner = false;
        },
        (err) => {
          router.navigate(["login"]);
          api.removeToken();
          this.showSpinner = false;
        }
      );
    } else {
      console.log("Token not Exists");
      token = queryParams["token"];
      if (token) {
        api.storeToken(token);
        api.verifyToken().subscribe(
          (res) => {
            router.navigate(["panel"]);
            this.showSpinner = false;
          },
          (err) => {
            router.navigate(["login"]);
            api.removeToken();
            this.showSpinner = false;
          }
        );
      } else {
        this.showSpinner = false;
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
