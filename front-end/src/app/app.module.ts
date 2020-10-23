import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { CalendarComponent } from "./calendar/calendar.component";
import { FoodAllocationComponent } from "./food-allocation/food-allocation.component";
import { LoginComponent } from "./login/login.component";
import { PanelComponent } from "./panel/panel.component";
import { AuthGuard } from "./_service/auth-guard";
import { TokenInterceptorService } from "./_service/token-interceptor.service";
import { CustomerManagementComponent } from './customer-management/customer-management.component';
import { OrdersComponent } from './orders/orders.component';
import { FoodSelectionComponent } from './food-selection/food-selection.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PanelComponent,
    CalendarComponent,
    FoodAllocationComponent,
    CustomerManagementComponent,
    OrdersComponent,
    FoodSelectionComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    FontAwesomeModule,
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
