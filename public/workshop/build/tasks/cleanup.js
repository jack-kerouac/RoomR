// Entfernt .js-Dateien, die beim kompilieren von .coffee-Dateien anfallen (natürlich
// _nachdem_ die .js-Dateien weiterverarbeitet worden sind)

module.exports = function(grunt){

  var fs = require('fs'); // Dateisystem-Funktionen

  grunt.registerMultiTask('cleanup', 'Clean up after the build process', function(){

    var files = grunt.file.expandFiles(this.data);
    files.forEach(function(file){
      var jsPath = file.split('.coffee')[0] + '.js';
      fs.unlinkSync(jsPath);
      grunt.log.ok('Cleaned up ' + jsPath);
    });

  });

};