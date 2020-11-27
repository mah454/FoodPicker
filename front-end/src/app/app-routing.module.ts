import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./login/login.component";
import { DrinkDefinitionComponent } from "./panel/food-management/drink-definition/drink-definition.component";
import { FoodDefinitionComponent } from "./panel/food-management/food-definition/food-definition.component";
import { FoodManagementComponent } from "./panel/food-management/food-management.component";
import { SaladDefinitionComponent } from "./panel/food-management/salad-definition/salad-definition.component";
import { PanelComponent } from "./panel/panel.component";
import { AuthGuard } from "./_services/AuthGuard.guard";

const routes: Routes = [
  {
    path: "panel",
    component: PanelComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: "food-management",
        component: FoodManagementComponent,
        children: [
          { path: "food-definition", component: FoodDefinitionComponent },
          { path: "drink-definition", component: DrinkDefinitionComponent },
          { path: "salad-definition", component: SaladDefinitionComponent },
          { path: "**", redirectTo: "food-definition", pathMatch: "full" },
        ],
      },
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
