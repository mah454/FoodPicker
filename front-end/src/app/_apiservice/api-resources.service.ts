import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { foodpicker } from "./food-picker-env";

@Injectable({
  providedIn: "root",
})
export class ApiResourcesService {
  constructor(private http: HttpClient) {}
  public login() {
    let obs = this.http.get("http://localhost:8080/api/v1/auth/login");
    obs.subscribe((e) => {
      console.log(e);
    });
  }
}
