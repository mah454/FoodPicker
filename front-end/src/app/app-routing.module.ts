import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CustomerManagementComponent } from "./customer-management/customer-management.component";
import { FoodAllocationComponent } from "./food-allocation/food-allocation.component";
import { FoodSelectionComponent } from "./food-selection/food-selection.component";
import { LoginComponent } from "./login/login.component";
import { OrdersComponent } from "./orders/orders.component";
import { PanelComponent } from "./panel/panel.component";
import { AuthGuard } from "./_service/auth-guard";

const routes: Routes = [
  {
    path: "panel",
    component: PanelComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: "",
        redirectTo: "food",
        pathMatch: "full",
      },
      {
        path: "food",
        component: FoodSelectionComponent,
      },
      {
        path: "allocation",
        component: FoodAllocationComponent,
      },
      {
        path: "customers",
        component: CustomerManagementComponent,
      },
      {
        path: "orders",
        component: OrdersComponent,
      },
      {
        path: "**",
        redirectTo: "food",
        pathMatch: "full",
      },
    ],
  },
  { path: "login", component: LoginComponent },
  { path: "**", redirectTo: "", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
