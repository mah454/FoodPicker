import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Food } from "../model/food";

@Injectable({
  providedIn: "root",
})
export class FoodService {
  constructor(private httpClient: HttpClient) {}

  public addFood(food: Food) {
    return this.httpClient.post("/api/v1/food/add", food, {
      observe: "response",
    });
  }
}
