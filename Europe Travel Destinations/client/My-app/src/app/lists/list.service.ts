import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ListService {
  private workingList: any[] = [];

  getWorkingList(): any[] {
    return this.workingList;
  }

  addToWorkingList(destination: any): void {
    if (!this.workingList.some(item => item.Destination === destination.Destination)) {
      this.workingList.push(destination);
    }
  }

  removeFromWorkingList(destination: any): void {
    this.workingList = this.workingList.filter(item => item.Destination !== destination.Destination);
  }

  clearWorkingList(): void {
    this.workingList = [];
  }
}
