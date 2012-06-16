// [Grunt](https://github.com/cowboy/grunt) ist ein simples, taskbasiertes Buildsystem
// aus Basis von Node. Es ist noch recht neu und nicht sehr fancy, dafür beliebt und
// einfach zu benutzen.

// Diese Zeile muss in jede Grunt-Datei. Sie stellt die `grunt`-API zur Verfügung. Leider
// mag Grunt nicht (von sich aus) mit CoffeeScript spielen, so dass wir im Buildsystem
// nicht an Vanilla-JS vorbeikommen.
module.exports = function(grunt){

  // Gesamte Konfiguration des Build-Prozesses
  grunt.initConfig({

    // Gesamten selbstgeschriebenen Non-CoffeeScript-Code (d.h. das Buildsystem) einem
    // Qualitätscheck mittels [JSHint](http://jshint.com) unterziehen.
    lint: {
      build: ['build/**/*.js'] // Alle .js-Files in build (inkl. Unterverzeichnisse)
    },

    // Haupt-.styl-File(s) nach CSS kompilieren. Alles was als Modul in die Haupt-Files
    // eingebunden ist, wird direkt in den Output hineinkompiliert.
    stylus: {
      main: {
        file: 'src/style/main.styl', // Quelldatei
        dest: 'app.css',             // Zieldatei
        options: {                   // Zusatzoptionen
          url: {
            paths: [ 'src/style' ]   // Pfad(e) für Bilder
          },
          compress: true             // Code-Komprimierung an/aus
        }
      }
    },

    // Sämtliches CoffeeScript nach JS kompilieren. Die dabei entstehenden Dateien werden
    // in den nächsten Schritten von QUnit getestet, vom Require.JS-Optimizer
    // zusammengefügt und komprimiert und schließlich vom Cleanup-Task gelöscht.
    coffee: {
      src: ['src/**/*.coffee'],  // Alle .coffee-Files in src (inkl. Unterverzeichnisse)
      test: ['test/**/*.coffee'] // Alle .coffee-Files in test (inkl. Unterverzeichnisse)
    },

    // JS-Tests mit QUnit in einer PhantomJS-Instanz durchführen. Quasi ein Browsertest
    // ohne Browser.
    qunit: {
      all: ['test/*.html']
    },

    // Dokumentation aus den .coffee-Files erstellen
    docco: {
      src: {
        files: [             // Zu dokumentierende Files
          'src/**/*.coffee',
          'grunt.js'
        ],
        dest: ''             // Zielverzeichnis, in dem `/docs` angelegt wird
      }
    },

    // RequireJS-Module zusammenfügen, komprimieren und optimieren
    requirejs: {
      baseUrl: './src/script',  // Script-Source-Verzeichnis
      paths: {                  // Pfade für Libraries mit doofen/abweichenden Dateinamen
        'jquery': 'lib/vendor/jquery-1.7.2',      // Versionsnummer
        'backbone': 'lib/vendor/backbone-amd',    // AMD-Build (https://github.com/amdjs)
        'underscore': 'lib/vendor/underscore-amd' // AMD-Build (https://github.com/amdjs)
      },
      name: 'main',             // Wurzelmodul
      out: 'app.js'             // Zieldatei
      //optimize: 'none'        // Optimierung deaktivieren (Debugging/Schnellerer Build)
    },

    // Cleanup; die aus CS generierten JS-Files entfernen
    cleanup: {
      src:  '<config:coffee.src>', // Alle Source-JS-Files
      test: '<config:coffee.test>' // Alle Test-JS-Files
    }

  });

  // Nicht fest in Grunt eingebaute Tasks laden
  grunt.loadTasks('build/tasks');         // Alle Tasks in build/task, z.B. der CS-Compiler
  grunt.loadNpmTasks('grunt-requirejs'); // Task in Form eines NPM-Moduls

  // Alle automatisch zu startenden Tasks unter dem Label 'default' ablegen
  // `cleanup` rauswerfen um die generierten JavaScript-Dateien zu inspizieren
  grunt.registerTask('default', 'lint stylus coffee qunit docco requirejs cleanup');

};