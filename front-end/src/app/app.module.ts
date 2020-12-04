import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { FoodComponent } from "./food/food.component";
import { LoginComponent } from "./login/login.component";
import { FoodManagementComponent } from "./panel/food-management/food-management.component";
import { PanelComponent } from "./panel/panel.component";
import { SpinnerComponent } from "./spinner/spinner.component";
import { AuthGuard } from "./_services/AuthGuard.guard";
import { TokenInterceptorInterceptor } from "./_services/token-interceptor.interceptor";
import { FoodFilterPipePipe } from './_services/food-filter-pipe.pipe';

@NgModule({
  declarations: [
    AppComponent,
    FoodManagementComponent,
    FoodComponent,
    LoginComponent,
    PanelComponent,
    SpinnerComponent,
    FoodFilterPipePipe,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
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
