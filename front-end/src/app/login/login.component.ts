import { Component, OnInit } from "@angular/core";
import { ApiResourcesService } from "../_apiservice/api-resources.service";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit(): void {}

  public login() {
    window.location.href = "http://localhost:8080/api/v1/auth/login";
    // let obs = this.http.get("/api/v1/auth/login");
    // obs.subscribe((e) => {
    // console.log(e);
    // });
  }
}
