import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-entry',
  templateUrl: './entry.component.html',
  styleUrls: ['./entry.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class EntryComponent implements OnInit {
//  public appVar: any;
  public slide: any;
  entry = '{background:black;}';
  public sections: string[] = [];

  constructor() {}

  ngOnInit() {
    const firstSection = '<div> <a herf="#"> link </a> </div>';
    this.sections[0] = firstSection;
  }

}
