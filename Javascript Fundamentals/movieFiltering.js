let mov = require(`./movies-250.json`);//Do not touch

let getBestMovieBasedOn = function(genre, minLength, dataset){
    
    let romanceMovies = dataset.movies.filter((movie) => movie.Genre.split(", ").includes(genre));
    let longRomanceMovies = romanceMovies.filter((movie)=>{
        
        runTimeHours = movie.Runtime.split(" ")[0]/60;
        return runTimeHours >= minLength;
    }
    )

    let bestMovie = longRomanceMovies.reduce((current, next)=>
    { 
        const currentRating = parseFloat(current.Ratings[0].Value);
        const nextRating = parseFloat(next.Ratings[0].Value);
        return currentRating > nextRating ? current : next;

    }
    );

    let title = bestMovie.Title;
    let yearReleased = bestMovie.Year;
    let rating = parseFloat(bestMovie.Ratings[0].Value);

    return(title + " released in " + yearReleased + " is the highest rated " + genre + " movie over " +
    minLength + " hour(s) long, with an imdb rating of " + rating);



};

 











 

 //Do not touch
console.log(getBestMovieBasedOn('Romance',2, mov));
module.exports=getBestMovieBasedOn;



