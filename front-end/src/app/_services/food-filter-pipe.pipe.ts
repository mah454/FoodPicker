import { Pipe, PipeTransform } from "@angular/core";
import { Food } from "../model/food";

@Pipe({
  name: "foodFilterPipe",
})
export class FoodFilterPipePipe implements PipeTransform {
  transform(foods: Food[], name: string): Food[] {
    return foods.filter((e) => e.name.includes(name));
  }
}
