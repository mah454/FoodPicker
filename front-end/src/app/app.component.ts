import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "./_service/auth.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
})
export class AppComponent {
  title = "FoodPicker";

  constructor(private router: Router, private auth: AuthService) {
    if (location.pathname === "/") {
      let token = this.auth.getToken();
      const len = this.getParameterLength(location.search);
      if (token) {
        this.auth.validateToken(token);
      } else if (len > 0) {
        const params = this.getParameters(location.search);
        token = params["token"];
        if (token) {
          this.auth.validateToken(token);
        } else {
          router.navigate(["login"]);
        }
      } else {
        router.navigate(["login"]);
      }
    }
  }

  private getParameters(url: string) {
    const parameters = {};
    url
      .substr(1)
      .split("&")
      .map((e) => (parameters[e.split("=")[0]] = e.split("=")[1]));
    return parameters;
  }

  private getParameterLength(url: string) {
    return Object.keys(this.getParameters(url)).length;
  }
}
