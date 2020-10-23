import { Component, OnInit } from "@angular/core";
import {
  faBars,
  faHandPointer,
  faList,
  faSign,
  faSignOutAlt,
  faUsersCog,
  faUtensils,
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-panel",
  templateUrl: "./panel.component.html",
  styleUrls: ["./panel.component.scss"],
})
export class PanelComponent implements OnInit {
  faBars = faBars;
  signOut = faSignOutAlt;
  sign = faSign;
  utensils = faUtensils;
  usersCog = faUsersCog;
  list = faList;
  handPoint = faHandPointer;

  constructor() {}

  ngOnInit(): void {}
}
