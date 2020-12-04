import { Component, OnInit } from "@angular/core";
import { Food, FoodType } from "src/app/model/food";
import { FoodService } from "src/app/_services/food.service";

@Component({
  selector: "app-feed-management",
  templateUrl: "./food-management.component.html",
  styleUrls: ["./food-management.component.scss"],
})
export class FoodManagementComponent implements OnInit {
  foodName: string = "";
  foodList: Array<Food> = [];
  filteredFoodList: Array<Food> = [];
  showSpinner = false;
  foodType: string = FoodType.FOOD.toString();

  constructor(private foodService: FoodService) {}

  ngOnInit(): void {
    this.getListFood();
  }

  swichTab(foodType: string) {
    Promise.resolve()
      .then(() => (this.foodType = foodType))
      .then(() => this.getListFood());
  }

  getListFood() {
    this.showSpinner = true;
    this.foodService.getAllFoods(FoodType[this.foodType]).subscribe(
      (res) => {
        this.foodList = res;
        this.showSpinner = false;
      },
      (err) => console.log(err)
    );
  }

  removeFood(food: Food) {
    this.showSpinner = true;
    this.foodService.deleteFood(food).subscribe(
      (res) => {
        let index = this.foodList.indexOf(food);
        this.foodList.splice(index, 1);
        this.showSpinner = false;
      },
      (err) => console.log("Error remove food")
    );
  }

  saveFood() {
    this.showSpinner = true;
    let food: Food = {
      id: 0,
      name: this.foodName,
      active: true,
      foodType: FoodType[this.foodType],
    };
    this.foodService.postFood(food).subscribe(
      (res) => {
        this.foodList.push(res);
        this.showSpinner = false;
        this.foodName = "";
      },
      (err) => {
        const status = err.status;
        this.showSpinner = false;
        console.log(status);
      }
    );
  }
}
