import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./login/login.component";
import { CalendarManagementComponent } from "./panel/calendar-management/calendar-management.component";
import { FoodManagementComponent } from "./panel/food-management/food-management.component";
import { PanelComponent } from "./panel/panel.component";
import { AuthGuard } from "./_services/AuthGuard.guard";

const routes: Routes = [
  {
    path: "panel",
    component: PanelComponent,
    canActivate: [AuthGuard],
    children: [
      { path: "food-management", component: FoodManagementComponent },
      { path: "calendar-management", component: CalendarManagementComponent },
      { path: "**", redirectTo: "food-management", pathMatch: "full" },
    ],
  },
  { path: "login", component: LoginComponent },
  { path: "**", redirectTo: "login", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
