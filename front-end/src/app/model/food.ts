export interface Food {
  id: Number;
  name: string;
  active: boolean;
  foodType: FoodType;
  // foodCategory: FoodCategory;
}

export enum FoodType {
  FOOD = "FOOD",
  DRINK = "DRINK",
  SALAD = "SALAD",
  DESSERT = "DESSERT",
}

export interface FoodCategory {
  name: String;
}
