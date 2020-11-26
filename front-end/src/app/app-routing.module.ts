import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CustomerManagementComponent } from "./pages/customer-management/customer-management.component";
import { FeedAllocationComponent } from "./pages/feed-allocation/feed-allocation.component";
import { FoodSelectionComponent } from "./pages/food-selection/food-selection.component";
import { LoginComponent } from "./pages/login/login.component";
import { OrdersComponent } from "./pages/orders/orders.component";
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
        redirectTo: "allocation",
        pathMatch: "full",
      },
      {
        path: "food",
        component: FoodSelectionComponent,
      },
      {
        path: "allocation",
        component: FeedAllocationComponent,
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
