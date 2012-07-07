// Kompiliert .styl-Dateien zu CSS

module.exports = function(grunt){

  var fs = require('fs');
  var path = require('path');
  var stylus = require('stylus'); // Der Stylus-Compiler
  var nib = require('nib');       // Die Stylus-Standard-Lib

  // Der Stylus-Compiler arbeitet asynchron, also brauchen wir an dieser Stelle auch einen
  // asynchronen (Multi)Task.
  grunt.registerMultiTask('stylus', 'Compile stylus files to css', function(){

    // `done()` aufrufen, sobald die asynchronen Teile des Tasks durch sind. Bis das
    // passiert, wartet Grunt auf den Task.
    var done = this.async();
    var file = grunt.file.expand(this.data.file)[0]; // Quelldatei
    var destination = this.data.dest;                // Zieldatei
    var options = this.data.options || {};           // Optionen (falls gesetzt)

    // Datei für Datei kompilieren und das Ergebnis in die Zieldatei schreiben. Erst wenn
    // der Callback ausgeführt wird, ist der Task erledigt und wir können `done()`
    // aufrufen.
    grunt.helper('stylus-file', file, options, function(css){
      fs.writeFileSync(destination, css);
      done();
    });

  });

  // Der Helper kompiliert eine Stylus-Datei und nimmt uns das Error Handling ab.
  grunt.registerHelper('stylus-file', function(file, options, callback){
    var input = fs.readFileSync(file, 'utf8');
    stylus(input)
    .set('compress', options.compress)
    .define('url', stylus.url({ paths: options.url.paths }))
    .use(nib())
    .render(function(err, css){
      if(err){
        throw err;
      }
      grunt.log.ok('Compiled ' + file);
      callback(css);
    });
  });

};