import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicListsComponent } from './public-lists.component';

describe('PublicListsComponent', () => {
  let component: PublicListsComponent;
  let fixture: ComponentFixture<PublicListsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PublicListsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicListsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
