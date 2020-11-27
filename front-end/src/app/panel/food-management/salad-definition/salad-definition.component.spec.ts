import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SaladDefinitionComponent } from './salad-definition.component';

describe('SaladDefinitionComponent', () => {
  let component: SaladDefinitionComponent;
  let fixture: ComponentFixture<SaladDefinitionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaladDefinitionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaladDefinitionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
