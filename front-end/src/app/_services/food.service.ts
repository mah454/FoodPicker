import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Food, FoodType } from "../model/food";
import { environment } from "./ApiEnvironment";

@Injectable({
  providedIn: "root",
})
export class FoodService {
  constructor(private httpClient: HttpClient) {}

  public postFood(food: Food): Observable<Food> {
    return this.httpClient.post<Food>(
      environment.BASE_URL + environment.FOOD_SAVE_PATH,
      food
    );
  }

  public getAllFoods(foodType: FoodType): Observable<Food[]> {
    const foodParams = {
      foodType: foodType.toString(),
    };
    return this.httpClient.get<Food[]>(
      environment.BASE_URL + environment.FOOD_LIST_PATH,
      { params: foodParams }
    );
  }

  public deleteFood(food: Food): Observable<void> {
    return this.httpClient.delete<void>(
      environment.BASE_URL + environment.FOOD_DELETE_PATH + food.id
    );
  }
}
