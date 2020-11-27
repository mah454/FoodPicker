import { Component, ElementRef, ViewChild } from "@angular/core";
import { ApiService } from "../_services/api.service";

@Component({
  selector: "app-panel",
  templateUrl: "./panel.component.html",
  styleUrls: ["./panel.component.scss"],
})
export class PanelComponent {
  constructor(private api: ApiService) {}
  @ViewChild("videoPlayer") videoPlayer: ElementRef;

  private index = 0;
  videoList = [
    "../../assets/Background_Videos/FoodPack1_01_Videvo.webm",
    "../../assets/Background_Videos/FoodPack1_03_Videvo.webm",
    "../../assets/Background_Videos/FoodPack1_12_Videvo.webm",
    "../../assets/Background_Videos/FoodPack1_14_Videvo.webm",
  ];

  videoEnded() {
    if (this.index == this.videoList.length - 1) {
      this.index = 0;
    } else {
      this.index++;
    }
    this.videoPlayer.nativeElement.abort = true;
    this.playVideo();
  }

  playVideo() {
    this.videoPlayer.nativeElement.src = this.videoList[this.index];
    this.videoPlayer.nativeElement.load();
    this.videoPlayer.nativeElement.muted = true;
    this.videoPlayer.nativeElement.play();
  }

  logout() {
    this.api.logout();
  }
}
