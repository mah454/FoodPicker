import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_service/auth.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  constructor(private auth: AuthService,private route: ActivatedRoute) {
  }

  ngOnInit(): void {}

  public login() {
    this.auth.login()
  }
}
