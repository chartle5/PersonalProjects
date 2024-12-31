const words = require('./words.json');
let synonyms = words.Synonyms;
let related = words.Related;
let nearAnt = words['Near Antonyms'];
let ant = words.Antonyms;
let counts = {
    Synonyms: 0,
    Related : 0,
    "Near Antonyms": 0,
    Antonyms: 0
}

let fs = require('fs');
let promise = new Promise(function(resolve,reject){
    
        let path = './Optimism_and_your_health.txt';
        fs.readFile(path, 'utf-8', (err,data) =>{
            if(err){
                reject("1Failed to read the file: " + err)
            }
            else{
                resolve(data);
            }
        });
    
    }

);
promise.then(function(result){
    result = result.toLowerCase();
    result = result.split(' ')
   
    for(word of result){
        for(w of synonyms)
        if(word.includes(w)){
            counts.Synonyms++;
        }
        for(w of related)
        if(word.includes(w)){
            counts.Related++;
        }
        for(w of nearAnt)
        if(word.includes(w)){
            counts['Near Antonyms']++;
        }
        for(w of ant)
        if(word.includes(w)){
            counts.Antonyms++;
        }

    }
    return new Promise(function(resolve,reject){
        path2 = './result.txt';
        fs.writeFile(path2, JSON.stringify(counts), 'utf-8', (err)=>{
            if(err){
                reject("Failed to write the file: " + err);
            } else{
                resolve();
            }
        })
    })
})
//setTimeout(function(){
    //console.log("{Synonyms:" + counts.Synonyms + ", Related:" + counts.Related + ", Near Antonyms:" + counts['Near Antonyms'] + ", Antonyms:" + counts.Antonyms + "}");

//},500)
