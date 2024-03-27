import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-screenshot-dialog',
  templateUrl: './screenshot-dialog.component.html',
  styleUrls: ['./screenshot-dialog.component.css']
})
export class ScreenshotDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: {url: string},
    public dialogRef: MatDialogRef<ScreenshotDialogComponent> // Добавлен MatDialogRef
  ) { }

  closeDialog(): void { // Новый метод для закрытия диалогового окна
    this.dialogRef.close();
  }
}
