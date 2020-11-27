import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { FoodComponent } from "./food/food.component";
import { LoginComponent } from "./login/login.component";
import { DrinkDefinitionComponent } from "./panel/food-management/drink-definition/drink-definition.component";
import { FoodDefinitionComponent } from "./panel/food-management/food-definition/food-definition.component";
import { FoodManagementComponent } from "./panel/food-management/food-management.component";
import { SaladDefinitionComponent } from "./panel/food-management/salad-definition/salad-definition.component";
import { PanelComponent } from "./panel/panel.component";
import { AuthGuard } from "./_services/AuthGuard.guard";
import { TokenInterceptorInterceptor } from "./_services/token-interceptor.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    FoodManagementComponent,
    FoodComponent,
    FoodDefinitionComponent,
    DrinkDefinitionComponent,
    SaladDefinitionComponent,
    LoginComponent,
    PanelComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule],
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
