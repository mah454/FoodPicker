import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BrowserModule, HammerModule } from "@angular/platform-browser";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { FoodComponent } from "./food/food.component";
import { LoginComponent } from "./login/login.component";
import { FoodManagementComponent } from "./panel/food-management/food-management.component";
import { PanelComponent } from "./panel/panel.component";
import { SpinnerComponent } from "./spinner/spinner.component";
import { AuthGuard } from "./_services/AuthGuard.guard";
import { FoodFilterPipePipe } from "./_services/food-filter-pipe.pipe";
import { TokenInterceptorInterceptor } from "./_services/token-interceptor.interceptor";
import { CalendarManagementComponent } from './calendar-management/calendar-management.component';

@NgModule({
  declarations: [
    AppComponent,
    FoodManagementComponent,
    FoodComponent,
    LoginComponent,
    PanelComponent,
    SpinnerComponent,
    FoodFilterPipePipe,
    CalendarManagementComponent,
  ],
  imports: [
    BrowserModule,
    HammerModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
