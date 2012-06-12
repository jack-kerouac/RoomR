// Erzeugt docs mit [Docco](http://jashkenas.github.com/docco/)

module.exports = function(grunt){

  var cp = require('child_process');

  // Neuen "MultiTask" anlegen. Multitaks arbeiten sich automatisch durch alle in der
  // Konfiguration angegebenen Targets
  grunt.registerMultiTask('docco', 'Create docco documentation', function(){
    var done = this.async();
    var files = grunt.file.expandFiles(this.data.files);
    var destination = this.data.dest;
    grunt.helper('docco-create', files, destination, function(){
      grunt.log.ok('Created docs for ' + files.join(', '));
      done();
    });
  });

  // Docco als Unterprozess aufrufen und `callback` ausführen wenn fertig.
  grunt.registerHelper('docco-create', function(files, destination, callback){
    var docco = cp.spawn('docco', files, {
      cwd: destination
    });
    docco.on('exit', callback);
  });

};