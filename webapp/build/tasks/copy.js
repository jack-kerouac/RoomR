module.exports = function(grunt) {

	grunt.registerMultiTask( "copy", "Copy files to destination folder and replace 1.8.19 with pkg.version", function() {
		function replaceVersion( source ) {
			return source.replace( "@VERSION", grunt.config( "pkg.version" ) );
		}
		var files = grunt.file.expandFiles( this.file.src );
		var target = this.file.dest + "/";
		var strip = this.data.strip;
		if ( typeof strip === "string" ) {
			strip = new RegExp( "^" + grunt.template.process( strip, grunt.config() ).replace( /[\-\[\]{}()*+?.,\\\^$|#\s]/g, "\\$&" ) );
		}
		files.forEach(function( fileName ) {
			var targetFile = strip ? fileName.replace( strip, "" ) : fileName;
			if ( /(js|css)$/.test( fileName ) ) {
				grunt.file.copy( fileName, target + targetFile, {
					process: replaceVersion
				});
			} else {
				grunt.file.copy( fileName, target + targetFile );
			}
		});
		grunt.log.writeln( "Copied " + files.length + " files." );
		var renameCount = 0;
		for ( var fileName in this.data.renames ) {
			renameCount += 1;
			grunt.file.copy( fileName, target + grunt.template.process( this.data.renames[ fileName ], grunt.config() ) );
		}
		if ( renameCount ) {
			grunt.log.writeln( "Renamed " + renameCount + " files." );
		}
	});

};