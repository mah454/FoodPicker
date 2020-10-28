import { Component, OnInit } from "@angular/core";
import { Food } from "../model/food";
import { FoodService } from "../_service/food.service";

@Component({
  selector: "app-food-allocation",
  templateUrl: "./food-allocation.component.html",
  styleUrls: ["./food-allocation.component.scss"],
})
export class FoodAllocationComponent implements OnInit {
  foodName: string;
  constructor(private foodService: FoodService) {}
  ngOnInit(): void {}

  public addFood() {
    const food = new Food(name);

    console.log(this.foodName);
    // this.foodService.addFood(food).subscribe(
    // (response) => console.log(response),
    // (err) => console.log(err),
    // () => console.log("FINISHED")
    // );
  }
}
