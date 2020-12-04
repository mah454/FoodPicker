import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Food } from "../model/food";
import { FoodService } from "../_services/food.service";

@Component({
  selector: "app-food",
  templateUrl: "./food.component.html",
  styleUrls: ["./food.component.scss"],
})
export class FoodComponent implements OnInit {
  @Input() food: Food;
  @Output() remove = new EventEmitter<Food>();

  constructor(private foodService: FoodService) {}

  ngOnInit(): void {}

  removeFood() {
    this.remove.emit(this.food);
  }
}
