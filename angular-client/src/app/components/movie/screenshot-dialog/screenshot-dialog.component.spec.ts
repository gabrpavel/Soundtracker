import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScreenshotDialogComponent } from './screenshot-dialog.component';

describe('ScreenshotDialogComponent', () => {
  let component: ScreenshotDialogComponent;
  let fixture: ComponentFixture<ScreenshotDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScreenshotDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ScreenshotDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
