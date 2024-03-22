import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MovieListComponent} from "./components/movie/movie-list/movie-list.component";
import {MovieDetailsComponent} from "./components/movie/movie-details/movie-details.component";

const routes: Routes = [
  {path: 'movies', component: MovieListComponent},
  {path: 'movies/:id', component: MovieDetailsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
