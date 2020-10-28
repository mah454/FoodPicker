import { Component, OnInit } from "@angular/core";
import { faCheck, faCheckDouble } from "@fortawesome/free-solid-svg-icons";
import * as jalaali from "../../../node_modules/jalaali-js/dist/jalaali.min.js";
import { Day } from "../model/day";

@Component({
  selector: "app-calendar",
  templateUrl: "./calendar.component.html",
  styleUrls: ["./calendar.component.scss"],
})
export class CalendarComponent implements OnInit {
  faCheck = faCheck;
  faDoubleCheck = faCheckDouble;
  private date: object;
  private gregorianDate: Date = new Date();

  private jalaliMonthNames = {
    1: "فروردین",
    2: "اردیبهشت",
    3: "خرداد",
    4: "تیر",
    5: "مرداد",
    6: "شهریور",
    7: "مهر",
    8: "آبان",
    9: "آذر",
    10: "دی",
    11: "بهمن",
    12: "اسفند",
  };

  private weekEndNames = ["ش", "ی", "د", "س", "چ", "پ", "ج"];

  private persianDigits = {
    0: "۰",
    1: "۱",
    2: "۲",
    3: "۳",
    4: "۴",
    5: "۵",
    6: "۶",
    7: "۷",
    8: "۸",
    9: "۹",
  };

  constructor() {}

  ngOnInit(): void {
    this.date = jalaali.toJalaali(this.gregorianDate);
    this.getSkipDays();
  }

  public getSkipDays() {
    const gregorianDate = this.getGregorianDate(1);

    // console.log(gregorianDate);
    const firstDayOfMon = new Date(
      gregorianDate["gy"],
      gregorianDate["gm"] - 1,
      gregorianDate["gd"]
    );

    const skipDays = firstDayOfMon.getDay() + 1;
    if (skipDays < 7) {
      return Array.from({ length: skipDays }, (_, i) => i + 1);
    }
  }

  private getGregorianDate(day: number) {
    return jalaali.toGregorian(
      this.getJalaliYear(),
      this.getJalaliMonth(),
      day
    );
  }

  public getCurrentMonth() {
    return this.jalaliMonthNames[this.date["jm"]];
  }

  public getWeekEndNames() {
    return this.weekEndNames;
  }

  public getDayOfWeek() {
    return this.gregorianDate.getDay();
  }

  public getJalaliYear() {
    return this.date["jy"];
  }

  public getJalaliMonth() {
    return this.date["jm"];
  }

  public getJalaliDay() {
    return this.date["jd"];
  }

  public getMonthDays() {
    const len = jalaali.jalaaliMonthLength(
      this.getJalaliYear(),
      this.getJalaliMonth()
    );

    let days: Array<Day> = [];
    for (let i = 1; i < len + 1; i++) {
      var day: Day = {
        num: i,
        isToday: this.isToday(i),
        isWeekEnd: this.isWeekEnd(i),
        cssClass: this.getDayClass(i),
        isHoliday: false,
        isLock: false,
      };
      days.push(day);
    }
    return days;
    // return Array.from({ length: len }, (_, i) => i + 1);
  }

  public convertToPersianDigits(num: number) {
    return num
      .toPrecision()
      .split("")
      .map((e) => this.persianDigits[e])
      .join("");
  }

  public isToday(dayNum: number) {
    return dayNum == this.getJalaliDay();
  }

  public isWeekEnd(dayNum: number) {
    const mDate = this.getGregorianDate(dayNum);
    const date = new Date(mDate["gy"], mDate["gm"] - 1, mDate["gd"]);
    return date.getDay() == 5;
  }

  public getDayClass(dayNum: number) {
    if (this.isToday(dayNum)) {
      return "today t1";
    } else if (this.isWeekEnd(dayNum)) {
      return "weekend";
    }
  }
}
