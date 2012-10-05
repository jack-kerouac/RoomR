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
			build: ['grunt.js', 'build/**/*.js'] // Alle .js-Files in build (inkl. Unterverzeichnisse)
		},


		copy: {
			images: {
				src: 'images',
				dest: '../public/images'
			},
			staticContent: {
				src: 'static',
				dest: '../public'
			}
		},

		// Haupt-.styl-File(s) nach CSS kompilieren. Alles was als Modul in die Haupt-Files
		// eingebunden ist, wird direkt in den Output hineinkompiliert.
		stylus: {
			main: {
				file: 'src/stylus/main.styl', // Quelldatei
				dest: '../public/styles/app-stylus.css',             // Zieldatei
				options: {                   // Zusatzoptionen
					url: {
						paths: [ 'src/stylus', 'images' ]   // Pfad(e) für Bilder
					},
					compress: false             // Code-Komprimierung an/aus
				}
			}
		},


		compass: {
			dev: {
				src: 'src/sass',
				dest: '../public/styles/',
				linecomments: true,
				forcecompile: true,
				require: 'susy',
				debugsass: true,
				images: 'images'
				//relativeassets: true
			}
		},

		coffee: {
			main: {
				dir: 'src/coffee/',
				dest: 'tmp/scripts/'
			}
			// test: {
			//   dir: 'test/coffee/**/*.coffee',
			//   dest: 'tmp/test/scripts/'
			// }
		},

		// JS-Tests mit QUnit in einer PhantomJS-Instanz durchführen. Quasi ein Browsertest
		// ohne Browser.
		qunit: {
			all: (function() {
				var files = grunt.file.expandFiles('test/*.html');
				var result = [];
				files.forEach(function(file){
					result.push('http://localhost:16000/' + file);
				});
				return result;
			})()
		},

		server: {
			port: 16000,
			base: '.'
		},

		// Dokumentation aus den .coffee-Files erstellen
		docco: {
			src: {
				files: [             // Zu dokumentierende Files
					'src/coffee/**/*.coffee',
					'build/tasks/*.js',
					'grunt.js'
				],
				dest: ''             // Zielverzeichnis, in dem `/docs` angelegt wird
			}
		},

		// RequireJS-Module zusammenfügen, komprimieren und optimieren
		requirejs: {
			baseUrl: 'tmp/scripts',  // Script-Source-Verzeichnis
			paths: {                  // Pfade für Libraries mit doofen/abweichenden Dateinamen
				'jquery': '../../src/js/lib/jquery-1.7.2',      // Versionsnummer
				'jquery-ui': '../../src/js/lib/jquery-ui-1.8.21.custom.min',
				'underscore': '../../src/js/lib/underscore-1.3.3-amd', // AMD-Build (https://github.com/amdjs)
				'backbone': '../../src/js/lib/backbone-0.9.2-amd',    // AMD-Build (https://github.com/amdjs)
				'knockout': '../../src/js/lib/knockout-2.1.0',
				'knockback': '../../src/js/lib/knockback-0.15.4-amd',
				'handlebars': '../../src/js/lib/Handlebars-1.0.beta.6', // AMD-Build (https://github.com/SlexAxton/require-handlebars-plugin)
				'modernizr': '../../src/js/lib/modernizr-2.5.3',
				'moment': '../../src/js/lib/moment-1.6.2',
				'moment-de': '../../src/js/lib/moment-de'
			},
			shim: {
				'modernizr': {
					exports: 'Modernizr'
				},
				'jquery': {
					exports: 'jQuery'
				},
				'jquery-ui': ['jquery'],
				'knockout': {
					exports: 'ko'
				},
				'handlebars': {
					exports: 'Handlebars'
				}
			},
			name: 'main',             // Wurzelmodul
			out: '../public/scripts/app.js',            // Zieldatei
			optimize: 'none'          // Optimierung deaktivieren (Debugging/Schnellerer Build)
		},

		// Cleanup; die aus CS generierten JS-Files entfernen
		// cleanup: {
		//   src:  '<config:coffee.src>', // Alle Source-JS-Files
		//   test: '<config:coffee.test>' // Alle Test-JS-Files
		// },

		clean: {
			folder: "tmp"
		},

		// if you get "Error: watch EMFILE" the reason is that watch uses inotifyd and thus can only monitor up to `sysctl -a | grep fs.inotify.max_user_instances` files
		// increase it with $ sudo sh -c 'echo 8192 > /proc/sys/fs/inotify/max_user_instances"
		watch: {
			files: ['src/**/*', 'images/**/*', 'static/**/*'],
			tasks: 'defaultWatch'
		}

	});

	// Nicht fest in Grunt eingebaute Tasks laden
	grunt.loadTasks('build/tasks');         // Alle Tasks in build/task, z.B. der CS-Compiler
	grunt.loadNpmTasks('grunt-requirejs'); // Task in Form eines NPM-Moduls
	grunt.loadNpmTasks('grunt-clean'); // Task in Form eines NPM-Moduls
	grunt.loadNpmTasks('grunt-compass');

	// Alle automatisch zu startenden Tasks unter dem Label 'default' ablegen
	// `cleanup` rauswerfen um die generierten JavaScript-Dateien zu inspizieren
	grunt.registerTask('default', 'lint compass coffee server docco requirejs copy clean');

	// TODO Flo: add compass:dev
	// grunt.registerTask('defaultWatch', 'lint compass coffee requirejs copy clean');
	grunt.registerTask('defaultWatch', 'compass copy clean');

	grunt.registerTask('styles', 'compass copy clean');
};
