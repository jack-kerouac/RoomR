// Kompiliert .coffee-Dateien zu .js-Dateien und legt diese am gleichen Ort ab

module.exports = function(grunt){

  var fs = require('fs');                // Dateisystem-Funktionen
  var coffee = require('coffee-script'); // Coffee-Script-Compiler

  // Neuen "MultiTask" anlegen. Multitaks arbeiten sich automatisch durch alle in der
  // Konfiguration angegebenen Targets
  grunt.registerMultiTask('coffee', 'Compile coffee files to js', function(){
    var files = grunt.file.expandFiles(this.data); // Platzhalter (* usw.) parsen
    files.forEach(function(file){ // Jede Datei durch den Helper jagen
      grunt.helper('coffee-file', file);
    });
  });

  // Jede einzelne CS-Datei wird durch diesen Helper kompiliert. Eine .js-Datei gleichen
  // Namens wird an der gleichen Stelle abgelegt
  grunt.registerHelper('coffee-file', function(file){
    var source = fs.readFileSync(file, 'utf8');
    var js;
    try {
      js = coffee.compile(source);
    }
    catch (e) {
      grunt.log.error('Failed to compile ' + file);
      grunt.log.error(e);
      throw e;
    }

    var jsPath = file.split('.coffee')[0] + '.js';
    fs.writeFileSync(jsPath, js);
    grunt.log.ok('Compiled ' + file);
  });

};