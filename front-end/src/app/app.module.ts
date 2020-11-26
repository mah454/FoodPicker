import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { CalendarComponent } from "./model-components/calendar/calendar.component";
import { FeedItemComponent } from "./model-components/feed-item/feed-item.component";
import { FeedPanelComponent } from "./model-components/feed-panel/feed-panel.component";
import { CustomerManagementComponent } from "./pages/customer-management/customer-management.component";
import { FeedAllocationComponent } from "./pages/feed-allocation/feed-allocation.component";
import { FoodSelectionComponent } from "./pages/food-selection/food-selection.component";
import { LoginComponent } from "./pages/login/login.component";
import { OrdersComponent } from "./pages/orders/orders.component";
import { PanelComponent } from "./panel/panel.component";
import { AuthGuard } from "./_service/auth-guard";
import { FoodFilterPipePipe } from "./_service/feed-filter-pipe.pipe";
import { TokenInterceptorService } from "./_service/token-interceptor.service";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PanelComponent,
    CalendarComponent,
    FeedAllocationComponent,
    CustomerManagementComponent,
    OrdersComponent,
    FoodSelectionComponent,
    FoodFilterPipePipe,
    FeedItemComponent,
    FeedPanelComponent,
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
