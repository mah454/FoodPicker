import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthService } from "./Auth.service";

@Injectable({
  providedIn: "root",
})
export class AuthGuard implements CanActivate {
  constructor(private api: AuthService, private router: Router) {}

  canActivate() {
    if (this.api.getToken()) {
      return true;
    } else {
      this.router.navigate(["/"]);
      return false;
    }
  }
}
