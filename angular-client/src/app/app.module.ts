// app.module.ts
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {MovieDetailsComponent} from "./components/movie/movie-details/movie-details.component";
import {MovieListComponent} from "./components/movie/movie-list/movie-list.component";
import {ScreenshotDialogComponent} from "./components/movie/screenshot-dialog/screenshot-dialog.component";
import {NgOptimizedImage} from "@angular/common";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgOptimizedImage,
  ],
  providers: [],
  declarations: [
    AppComponent,
    MovieListComponent,
    MovieDetailsComponent,
    ScreenshotDialogComponent,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
